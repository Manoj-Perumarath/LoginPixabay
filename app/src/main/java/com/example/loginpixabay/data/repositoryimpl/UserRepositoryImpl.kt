package com.example.loginpixabay.data.repositoryimpl

import com.example.loginpixabay.data.api.ApiService
import com.example.loginpixabay.data.model.AuthRequest
import com.example.loginpixabay.data.model.LoginResponse
import com.example.loginpixabay.data.model.RegistrationRequest
import com.example.loginpixabay.data.model.ResourceState
import com.example.loginpixabay.data.repository.UserRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : UserRepository {
    override suspend fun login(loginRequest: AuthRequest): ResourceState<LoginResponse> {
        delay(1000)
        return if (loginRequest.email == "test@example.com" && loginRequest.password == "password") {
            ResourceState.Success(LoginResponse(message = "Login successful"))
        } else
            ResourceState.Failure("Invalid credentials")
    }

    override suspend fun register(registerRequest: RegistrationRequest): ResourceState<LoginResponse> {
        delay(1000)
        return ResourceState.Success(LoginResponse(message = "Registration successful"))
    }
}