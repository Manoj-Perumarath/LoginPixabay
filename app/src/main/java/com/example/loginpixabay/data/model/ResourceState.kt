package com.example.loginpixabay.data.model

sealed class ResourceState<T> {
    class Loading<T> : ResourceState<T>()
    class Success<T>(val data: T) : ResourceState<T>()
    class Failure<T>(val error: Any) : ResourceState<T>()
}