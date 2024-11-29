package com.example.loginpixabay.data.model

data class RegFormState(
    val emailError: String? = null,
    val ageError: String?=null,
    val passwordError: String? = null
) {
    fun hasErrors(): Boolean = emailError != null || ageError!=null || passwordError != null
}
