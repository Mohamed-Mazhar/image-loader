package com.example.photodisplayer.features.photodetails.domain.usecases

import com.example.photodisplayer.features.photos.domain.entities.Image

interface UpdatePhotoDetailsUsecase {
    suspend fun execute(image: Image)
}