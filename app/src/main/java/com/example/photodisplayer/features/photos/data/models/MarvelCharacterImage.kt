package com.example.photodisplayer.features.photos.data.models

import com.google.gson.annotations.SerializedName

data class MarvelCharacterImage(
    @SerializedName("path")
    val path: String,
    @SerializedName("extension")
    val extension: String
)