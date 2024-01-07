package com.example.photodisplayer.features.photodetails.presentation.viewmodel

import com.example.photodisplayer.features.photos.domain.entities.MarvelCharacter

data class PhotoDetailsState(
    val marvelCharacter: MarvelCharacter? = null,
    val updateCompleted: Boolean = false,
    val isLoading: Boolean = false,
)