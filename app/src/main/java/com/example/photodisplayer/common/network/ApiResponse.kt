package com.example.photodisplayer.common.network

sealed class ApiResponse<out T: Any> {
    class Success<out T : Any>(val data: T) : ApiResponse<T>()
    class Error(val exception: Throwable) : ApiResponse<Nothing>()
}