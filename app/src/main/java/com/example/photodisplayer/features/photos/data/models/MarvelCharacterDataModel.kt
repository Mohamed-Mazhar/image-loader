package com.example.photodisplayer.features.photos.data.models

import com.google.gson.annotations.SerializedName

data class MarvelCharacterDataModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("thumbnail")
    val marvelCharacterImage: MarvelCharacterImage
)