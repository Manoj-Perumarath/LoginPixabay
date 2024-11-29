package com.example.loginpixabay.ui.screens.registration

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
import com.example.loginpixabay.databinding.FragmentRegistrationBinding
import com.example.loginpixabay.ui.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    companion object {
        fun newInstance() = RegistrationFragment()
    }

    private lateinit var binding: FragmentRegistrationBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_registration, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.regResult.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResourceState.Loading -> {
                    binding.progress.show()
                }

                is ResourceState.Success -> {
                    binding.progress.hide()
                    Toast.makeText(requireContext(), state.data.message, Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigate(R.id.action_registration_to_home)
                }

                is ResourceState.Failure -> {
                    binding.progress.hide()
                    Toast.makeText(requireContext(), state.error.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        viewModel.regFormState.observe(viewLifecycleOwner) { formState ->
            formState.emailError?.let { binding.emailInputLayout.error = it }
            formState.passwordError?.let { binding.passwordInputLayout.error = it }
            formState.ageError?.let { binding.ageInputLayout.error = it }
        }

        viewModel.navigationEvent.observe(viewLifecycleOwner) { event ->
            if (event == "navigate_to_login") {
                findNavController().navigate(R.id.action_registration_to_login)
                viewModel.clearNavigationEvent()
            }
        }
    }
}