package com.example.photodisplayer.features.photodetails.data.models.apiresponse

import com.google.gson.annotations.SerializedName

data class ShrinkApiResponse(
    @SerializedName("output")
    val shrinkData: ShrinkData
)

data class ShrinkData(
    @SerializedName("size")
    val size: Int,
    @SerializedName("url")
    val shrinkUrl: String
)

