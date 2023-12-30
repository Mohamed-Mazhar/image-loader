package com.example.photodisplayer.features.photodetails.domain.usecasesimpl

import com.example.photodisplayer.features.photodetails.domain.usecases.UpdatePhotoDetailsUsecase
import com.example.photodisplayer.features.photos.data.repositories.ImageRepository
import com.example.photodisplayer.features.photos.domain.entities.MarvelCharacter

class UpdatePhotoDetailsUsecaseImpl(
    private val imageRepository: ImageRepository
): UpdatePhotoDetailsUsecase {
    override suspend fun execute(marvelCharacter: MarvelCharacter) {
        imageRepository.updatePhoto(marvelCharacter)
    }
}