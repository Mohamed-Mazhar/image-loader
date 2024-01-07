package com.example.photodisplayer.features.photodetails.data.repositories

import com.example.photodisplayer.common.network.ApiHandler
import com.example.photodisplayer.common.network.ApiResponse
import com.example.photodisplayer.features.photodetails.data.datasources.api.TinifyWebService
import com.example.photodisplayer.features.photodetails.data.models.apiresponse.ShrinkApiResponse
import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TinifyRepository(
    private val tinifyWebService: TinifyWebService,
) : ApiHandler() {

    suspend fun compress(imageUrl: String): ApiResponse<ShrinkApiResponse> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("url", imageUrl)
        return handlePostRequestResponse(
            execute = tinifyWebService::shrinkImage,
            parameters = mapOf("source" to jsonObject)
        ) {
            it
        }
    }

}

data class ImageUrl(
    @SerializedName("source")
    @Expose
    val source: UrlObject,
)

data class UrlObject(
    @SerializedName("url")
    @Expose
    val url: String,
)