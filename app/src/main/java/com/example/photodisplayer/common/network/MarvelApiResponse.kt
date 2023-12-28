package com.example.photodisplayer.common.network

import com.google.gson.annotations.SerializedName


class MarvelApiResponse<T>(
    @SerializedName("code")
    val code: Int = 200,
    @SerializedName("status")
    val status: String = "",
    @SerializedName("data")
    val marvelData: MarvelData<T>
)

data class MarvelData<T>(
    @SerializedName("results")
    val results: List<T>
)

