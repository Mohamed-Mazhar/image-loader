package com.example.photodisplayer.features.photos.domain.usecasesimpl

import com.example.photodisplayer.features.photos.data.repositories.ImageRepository
import com.example.photodisplayer.features.photos.domain.entities.Image
import com.example.photodisplayer.features.photos.domain.usecases.AddImageUsecase

class AddImageUsecaseImpl(
    private val imageRepository: ImageRepository
): AddImageUsecase {

    override suspend fun add(image: Image) {
        imageRepository.addPhoto(image)
    }
}