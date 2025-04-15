package com.Mobile_RMP.application.utils

import android.content.Context
import androidx.core.content.edit

object AuthPreferences {
    private const val PREFS_NAME = "auth_prefs"
    private const val KEY_TOKEN = "token"

    fun saveToken(token: String) {
        val prefs = getPrefs()
        prefs.edit { putString(KEY_TOKEN, token) }
    }

    fun getToken(): String? {
        return getPrefs().getString(KEY_TOKEN, null)
    }

    fun clearToken() {
        getPrefs().edit { remove(KEY_TOKEN) }
    }

    private fun getPrefs() =
        appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private lateinit var appContext: Context
    fun init(context: Context) {
        appContext = context.applicationContext
    }
}