package com.example.photodisplayer.features.photodetails.domain.usecases

import com.example.photodisplayer.features.photos.domain.entities.Image

interface GetPhotoByIdUsecase {
    suspend fun execute(id: String): Image
}