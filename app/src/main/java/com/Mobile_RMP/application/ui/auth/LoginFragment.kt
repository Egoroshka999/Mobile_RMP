package com.Mobile_RMP.application.ui.auth

// Basic Fragment imports
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

// ViewModel imports

// Navigation imports
import androidx.navigation.fragment.findNavController
import com.Mobile_RMP.application.R

import com.Mobile_RMP.application.databinding.FragmentLoginBinding
import com.Mobile_RMP.application.utils.AuthPreferences

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (AuthPreferences.getToken() != null) {
            findNavController().navigate(R.id.homeFragment)
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            viewModel.login(username, password)
        }

        binding.btnToRegister.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_register)
        }

        viewModel.authResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is AuthViewModel.AuthResult.Success -> {
                    try {
                        findNavController().navigate(R.id.action_login_to_home)
                    } finally {

                    }

                }
                is AuthViewModel.AuthResult.Error -> {
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}