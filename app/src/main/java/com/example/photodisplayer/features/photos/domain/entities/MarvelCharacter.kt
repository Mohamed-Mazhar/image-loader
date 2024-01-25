package com.example.photodisplayer.features.photos.domain.entities

data class MarvelCharacter(
    val id: String,
    val name: String,
    val caption: String,
    val imagePath: String,
    val height: Int? = null,
    val width: Int? = null
)