package com.example.photodisplayer.common.network

import com.example.photodisplayer.common.util.generateHash
import com.example.photodisplayer.features.photos.data.datasources.api.MarvelWebService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object MarvelApiService {
    private const val MARVEL_BASE_URL = "https://gateway.marvel.com/"
    private const val MARVEL_PUBLIC_KEY = "a2507665325bab07c7c1675fd8ad255d"
    private const val MARVEL_PRIVATE_KEY = "8d3bd6d82568d30e3bf18b129c5bc2aa2b71c5a1"

    private val retrofit: Retrofit by lazy {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val currentTime = System.currentTimeMillis()

        val apiKeyInterceptor = Interceptor { chain ->
            val hash = generateHash(
                publicKey = MARVEL_PUBLIC_KEY,
                privateKey = MARVEL_PRIVATE_KEY,
                timestamp = currentTime
            )
            val originalRequest = chain.request()
            val originalHttpUrl = originalRequest.url

            val timestampUrl = originalHttpUrl.newBuilder()
                .addQueryParameter("apikey", MARVEL_PUBLIC_KEY)
                .addQueryParameter("hash", hash)
                .addQueryParameter("ts", currentTime.toString())
                .build()

            val requestBuilder = originalRequest.newBuilder()
                .url(timestampUrl)
            chain.proceed(requestBuilder.build())
        }

        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl(MARVEL_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    val marvelWebService: MarvelWebService by lazy {
        retrofit.create(MarvelWebService::class.java)
    }


}