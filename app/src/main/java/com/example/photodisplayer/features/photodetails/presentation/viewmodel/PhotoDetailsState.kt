package com.example.photodisplayer.features.photodetails.presentation.viewmodel

import com.example.photodisplayer.features.photos.domain.entities.Image

data class PhotoDetailsState(
    val image: Image? = null,
    val updateCompleted: Boolean = false,
    val isLoading: Boolean = false,
)