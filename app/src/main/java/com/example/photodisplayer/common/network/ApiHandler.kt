package com.example.photodisplayer.common.network


import retrofit2.HttpException
import retrofit2.Response
import kotlin.reflect.KSuspendFunction1

abstract class ApiHandler {
    suspend fun <T : Any, RESULT: Any> handleGetRequestResponse(
        execute: suspend () -> Response<T>,
        map: (input: T) -> RESULT
    ): ApiResponse<RESULT> {
        return try {
            val response = execute()
            checkResponse(response, map)
        } catch (e: HttpException) {
            ApiResponse.Error(
                WebserviceException(code = e.code(), errorMessage = e.message(), description = "")
            )
        } catch (e: Throwable) {
            ApiResponse.Error(e)
        }
    }

    suspend fun <T : Any, RESULT: Any> handlePostRequestResponse(
        execute: suspend (params: Map<String, Any>) -> Response<T>,
        parameters: Map<String, Any>,
        map: (input: T) -> RESULT
    ): ApiResponse<RESULT> {
        return try {
            val response = execute(parameters)
            checkResponse(response, map)
        } catch (e: HttpException) {
            ApiResponse.Error(
                WebserviceException(code = e.code(), errorMessage = e.message(), description = "")
            )
        } catch (e: Throwable) {
            ApiResponse.Error(e)
        }
    }

    private fun <T : Any, RESULT: Any> checkResponse(
        response: Response<T>,
        map: (input: T) -> RESULT
    ): ApiResponse<RESULT> {
        return if (response.isSuccessful) {
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
    }
}