package com.example.photodisplayer.features.photos.domain.usecasesimpl

import com.example.photodisplayer.features.photos.data.repositories.ImageRepository
import com.example.photodisplayer.features.photos.domain.usecases.UpdateImageWidthAndHeight

class UpdateImageWidthAndHeightImpl(
    private val imageRepository: ImageRepository
): UpdateImageWidthAndHeight {
    override suspend fun execute(photoId: String, width: Int, height: Int) {
        val photo = imageRepository.getPhotoById(photoId)
        val updatedPhoto = photo.copy(
            height = height,
            width = width
        )
        imageRepository.updatePhoto(updatedPhoto)
    }
}