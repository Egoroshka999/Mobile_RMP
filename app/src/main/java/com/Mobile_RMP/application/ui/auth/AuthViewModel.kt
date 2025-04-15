package com.Mobile_RMP.application.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.Mobile_RMP.application.utils.RetrofitClient
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import com.Mobile_RMP.application.api.AuthRequest
import com.Mobile_RMP.application.utils.AuthPreferences

class AuthViewModel : ViewModel() {
    private val apiService = RetrofitClient.instance

    sealed class AuthResult {
        object Success : AuthResult()
        data class Error(val message: String) : AuthResult()
    }

    private val _authResult = MutableLiveData<AuthResult>()
    val authResult: LiveData<AuthResult> = _authResult

    fun register(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.register(AuthRequest(username, password))
                if (response.isSuccessful) {
                    _authResult.postValue(AuthResult.Success)
                } else {
                    _authResult.postValue(AuthResult.Error("Registration failed"))
                }
            } catch (e: Exception) {
                _authResult.postValue(AuthResult.Error(e.message ?: "Unknown error"))
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.login(AuthRequest(username, password))
                if (response.isSuccessful) {
                    response.body()?.token?.let {
                        AuthPreferences.saveToken(it)
                        _authResult.postValue(AuthResult.Success)
                    }
                } else {
                    _authResult.postValue(AuthResult.Error("Login failed"))
                }
            } catch (e: Exception) {
                _authResult.postValue(AuthResult.Error(e.message ?: "Unknown error"))
            }
        }
    }
}