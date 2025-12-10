package com.example.photodisplayer.features.photodetails.domain.usecasesimpl

import com.example.photodisplayer.features.photodetails.domain.usecases.UpdatePhotoDetailsUsecase
import com.example.photodisplayer.features.photos.data.repositories.ImageRepository
import com.example.photodisplayer.features.photos.domain.entities.Image

class UpdatePhotoDetailsUsecaseImpl(
    private val imageRepository: ImageRepository
): UpdatePhotoDetailsUsecase {
    override suspend fun execute(image: Image) {
        imageRepository.updatePhoto(image)
    }
}