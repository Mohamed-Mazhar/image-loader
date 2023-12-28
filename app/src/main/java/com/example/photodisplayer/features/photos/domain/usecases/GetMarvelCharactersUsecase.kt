package com.example.photodisplayer.features.photos.domain.usecases


import com.example.photodisplayer.common.network.ApiResponse
import com.example.photodisplayer.features.photos.domain.entities.MarvelCharacter

interface GetMarvelCharactersUsecase {
    suspend fun get(): ApiResponse<List<MarvelCharacter>>
}