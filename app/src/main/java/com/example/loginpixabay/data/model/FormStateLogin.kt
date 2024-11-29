package com.example.loginpixabay.data.model

data class FormState(val emailError: String? = null, val passwordError: String? = null) {
    fun hasErrors(): Boolean = emailError != null || passwordError != null
}
