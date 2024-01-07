package com.example.photodisplayer.features.photodetails.data.datasources.api

import com.example.photodisplayer.common.network.Urls
import com.example.photodisplayer.features.photodetails.data.models.apiresponse.ShrinkApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

@JvmSuppressWildcards
interface TinifyWebService {

    @POST(Urls.TINIFY.SHRINK)
    suspend fun shrinkImage(@Body body: Map<String, Any>): Response<ShrinkApiResponse>

}