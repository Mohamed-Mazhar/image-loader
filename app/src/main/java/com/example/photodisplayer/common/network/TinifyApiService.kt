package com.example.photodisplayer.common.network

import com.example.photodisplayer.common.util.encodeToBase64
import com.example.photodisplayer.features.photodetails.data.datasources.api.TinifyWebService
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object TinifyApiService {
    private const val TINIFY_BASE_URL = "https://api.tinify.com/"
    private const val TINIFY_API_KEY = "HfW3HFWMJ38Sv8STdDFq3hqBH5QqbqVY"

    private val retrofit: Retrofit by lazy {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val encodedAuth = encodeToBase64("api", TINIFY_API_KEY)

        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor {
                val newRequest: Request = it.request().newBuilder()
                    .addHeader("Authorization", "Basic $encodedAuth")
                    .addHeader("Content-Type", "application/json")
                    .build()
                it.proceed(newRequest)
            }
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl(TINIFY_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    val tinifyApiService: TinifyWebService by lazy {
        retrofit.create(TinifyWebService::class.java)
    }

}