package com.example.photodisplayer.features.photos.data.repositories

import com.example.photodisplayer.common.network.ApiHandler
import com.example.photodisplayer.features.photos.data.datasources.api.MarvelApiService
import com.example.photodisplayer.features.photos.data.mappers.MarvelCharacterDataMapper


class ImageRepository(
    private val marvelApiService: MarvelApiService
) : ApiHandler() {

    suspend fun getMarvelCharacters() = handle(marvelApiService::getMarvelCharacters) {
        MarvelCharacterDataMapper.toEntities(
            marvelCharacterDataModels = it.marvelData.results
        )
    }
}