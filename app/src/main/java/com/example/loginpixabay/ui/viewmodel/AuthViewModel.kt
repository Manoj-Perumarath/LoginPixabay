package com.example.loginpixabay.ui.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginpixabay.data.model.AuthRequest
import com.example.loginpixabay.data.model.FormState
import com.example.loginpixabay.data.model.LoginResponse
import com.example.loginpixabay.data.model.RegFormState
import com.example.loginpixabay.data.model.RegistrationRequest
import com.example.loginpixabay.data.model.ResourceState
import com.example.loginpixabay.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {
    private val _loginResult = MutableLiveData<ResourceState<LoginResponse>>()
    val loginResult: LiveData<ResourceState<LoginResponse>> get() = _loginResult

    private val _regResult = MutableLiveData<ResourceState<LoginResponse>>()
    val regResult: LiveData<ResourceState<LoginResponse>> get() = _regResult

    val email = MutableLiveData<String>()
    val age = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _formState = MutableLiveData<FormState>()
    val formState: LiveData<FormState> get() = _formState

    private val _regFormState = MutableLiveData<RegFormState>()
    val regFormState: LiveData<RegFormState> get() = _regFormState

    private val _navigationEvent = MutableLiveData<String?>()
    val navigationEvent: MutableLiveData<String?> get() = _navigationEvent

    fun validateForm(email: String, password: String) {
        val emailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val passwordValid = password.length in 6..12

        if (!emailValid || !passwordValid) {
            _formState.value = FormState(
                emailError = if (!emailValid) "Invalid Email" else null,
                passwordError = if (!passwordValid) "Password must be 6-12 chars" else null
            )
        } else {
            _formState.value = FormState()
        }
    }

    fun login() = viewModelScope.launch {
        val emailValue = email.value.orEmpty()
        val passwordValue = password.value.orEmpty()
        validateForm(emailValue, passwordValue)
        if (_formState.value?.hasErrors() == false) {
            _loginResult.value = ResourceState.Loading()
            viewModelScope.launch {
                val loginRequest = AuthRequest(emailValue, passwordValue)
                val result = repository.login(loginRequest)
                _loginResult.postValue(result)
            }
        }
    }

    private fun validateRegistrationForm(email: String, password: String, age: String) {
        val emailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val passwordValid = password.length in 6..12
        val ageValid = age.toIntOrNull()?.let { it in 18..99 } ?: false

        _regFormState.value = RegFormState(
            emailError = if (!emailValid) "Invalid Email" else null,
            ageError = if (!ageValid) "Age must be between 18 and 99" else null,
            passwordError = if (!passwordValid) "Password must be 6-12 characters" else null
        )
    }

    fun onBackToLogin() {
        _navigationEvent.value = "navigate_to_login"
    }

    fun clearNavigationEvent() {
        _navigationEvent.value = null
    }

    fun onRegister() {
        val emailValue = email.value.orEmpty()
        val passwordValue = password.value.orEmpty()
        val ageValue = age.value.orEmpty()

        validateRegistrationForm(emailValue, passwordValue, ageValue)

        if (_regFormState.value?.hasErrors() == false) {
            _regResult.value = ResourceState.Loading()
            viewModelScope.launch {
                val result = repository.register(
                    RegistrationRequest(
                        emailValue,
                        ageValue.toInt(),
                        passwordValue
                    )
                )
                _regResult.postValue(result)
            }
        }
    }
}

