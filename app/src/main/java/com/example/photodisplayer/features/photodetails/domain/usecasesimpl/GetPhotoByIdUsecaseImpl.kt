package com.example.photodisplayer.features.photodetails.domain.usecasesimpl

import com.example.photodisplayer.features.photodetails.domain.usecases.GetPhotoByIdUsecase
import com.example.photodisplayer.features.photos.data.repositories.ImageRepository
import com.example.photodisplayer.features.photos.domain.entities.Image

class GetPhotoByIdUsecaseImpl(
    private val imageRepository: ImageRepository
): GetPhotoByIdUsecase {
    override suspend fun execute(id: String): Image {
        return imageRepository.getPhotoById(id)
    }
}