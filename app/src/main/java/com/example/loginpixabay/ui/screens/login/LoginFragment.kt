package com.example.loginpixabay.ui.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.loginpixabay.R
import com.example.loginpixabay.data.model.ResourceState
import com.example.loginpixabay.databinding.FragmentLoginBinding
import com.example.loginpixabay.ui.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loginResult.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResourceState.Loading -> {
                    binding.progress.show()
                }

                is ResourceState.Success -> {
                    binding.progress.hide()
                    Toast.makeText(requireContext(), state.data.message, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_login_to_home)
                }

                is ResourceState.Failure -> {
                    binding.progress.hide()
                    if (state.error == "navigate_to_register") {
                        findNavController().navigate(R.id.action_login_to_registration)
                    } else {
                        Toast.makeText(requireContext(), state.error.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        viewModel.formState.observe(viewLifecycleOwner) { formState ->
            formState.emailError?.let { binding.emailInputLayout.error = it }
            formState.passwordError?.let { binding.passwordInputLayout.error = it }
        }

        binding.loginButton.setOnClickListener {
            viewModel.login()
        }

        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_registration)
        }
    }
}
