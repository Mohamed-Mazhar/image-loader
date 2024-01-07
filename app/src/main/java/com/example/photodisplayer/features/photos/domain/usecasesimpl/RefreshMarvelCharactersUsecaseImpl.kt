package com.example.photodisplayer.features.photos.domain.usecasesimpl

import com.example.photodisplayer.common.network.ApiResponse
import com.example.photodisplayer.common.network.WebserviceException
import com.example.photodisplayer.features.photos.data.repositories.ImageRepository
import com.example.photodisplayer.features.photos.domain.usecases.RefreshMarvelCharactersUsecase

class RefreshMarvelCharactersUsecaseImpl(
    private val imageRepository: ImageRepository,
) : RefreshMarvelCharactersUsecase {
    override suspend fun execute() {
        when (val response = imageRepository.getMarvelCharacters()) {
            is ApiResponse.Success -> {
                imageRepository.addPhotos(response.data)
            }

            is ApiResponse.Error -> {
                throw response.exception
            }
        }
    }
}