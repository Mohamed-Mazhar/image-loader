package com.example.photodisplayer.features.photos.domain.usecasesimpl

import com.example.photodisplayer.common.network.ApiResponse
import com.example.photodisplayer.features.photos.data.repositories.ImageRepository
import com.example.photodisplayer.features.photos.domain.usecases.RefreshMarvelCharactersUsecase

class RefreshMarvelCharactersUsecaseImpl(
    private val imageRepository: ImageRepository
) : RefreshMarvelCharactersUsecase {
    override suspend fun execute() {
        val response = imageRepository.getMarvelCharacters()
        if (response is ApiResponse.Success) {
            imageRepository.addPhotos(response.data)
        }
    }
}