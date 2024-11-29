package com.example.loginpixabay.data.repository

import com.example.loginpixabay.data.model.AuthRequest
import com.example.loginpixabay.data.model.LoginResponse
import com.example.loginpixabay.data.model.RegistrationRequest
import com.example.loginpixabay.data.model.ResourceState

interface UserRepository {

    suspend fun login(loginRequest: AuthRequest):ResourceState<LoginResponse>
    suspend fun register(registerRequest: RegistrationRequest):ResourceState<LoginResponse>
}