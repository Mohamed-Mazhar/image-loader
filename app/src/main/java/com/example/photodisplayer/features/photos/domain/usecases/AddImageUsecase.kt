package com.example.photodisplayer.features.photos.domain.usecases

import com.example.photodisplayer.features.photos.domain.entities.Image

interface AddImageUsecase {
    suspend fun add(image: Image)
}