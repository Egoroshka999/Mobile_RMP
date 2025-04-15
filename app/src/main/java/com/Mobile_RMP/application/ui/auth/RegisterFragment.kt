package com.Mobile_RMP.application.ui.auth


// Basic Fragment imports
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

// Navigation imports
import androidx.navigation.fragment.findNavController
import com.Mobile_RMP.application.R

import com.Mobile_RMP.application.databinding.FragmentRegisterBinding
import com.Mobile_RMP.application.utils.AuthPreferences

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
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
            viewModel.register(username, password)
        }

        binding.btnToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_register_to_login)
        }

        viewModel.authResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is AuthViewModel.AuthResult.Success -> {
                    Toast.makeText(requireContext(), "Registered!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_register_to_login)
                }
                is AuthViewModel.AuthResult.Error -> {
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}