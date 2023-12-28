package com.example.photodisplayer.features.photos.domain.usecasesimpl

import com.example.photodisplayer.common.network.ApiResponse
import com.example.photodisplayer.features.photos.data.repositories.ImageRepository
import com.example.photodisplayer.features.photos.domain.entities.MarvelCharacter
import com.example.photodisplayer.features.photos.domain.usecases.GetMarvelCharactersUsecase

class GetMarvelCharactersUsecaseImpl(
    private val imageRepository: ImageRepository
): GetMarvelCharactersUsecase {

    override suspend fun get(): ApiResponse<List<MarvelCharacter>> {
        return imageRepository.getMarvelCharacters()
    }
}