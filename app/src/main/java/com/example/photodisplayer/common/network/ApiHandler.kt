package com.example.photodisplayer.common.network


import retrofit2.HttpException
import retrofit2.Response

abstract class ApiHandler {

    suspend fun <T : Any, RESULT: Any> handle(
        execute: suspend () -> Response<T>,
        map: (input: T) -> RESULT
    ): ApiResponse<RESULT> {
        return try {
            val response = execute()
            if (response.isSuccessful) {
                val returnedMappedResult = map(response.body()!!)
                ApiResponse.Success(returnedMappedResult)
            } else {
                ApiResponse.Error(
                    WebserviceException(
                        code = response.code(),
                        description = response.errorBody()?.string() ?: "",
                        errorMessage = "Unknown Error"
                    )
                )
            }
        } catch (e: HttpException) {
            ApiResponse.Error(
                WebserviceException(code = e.code(), errorMessage = e.message(), description = "")
            )
        } catch (e: Throwable) {
            ApiResponse.Error(e)
        }
    }
}