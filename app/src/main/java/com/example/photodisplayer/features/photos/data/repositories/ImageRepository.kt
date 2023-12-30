package com.example.photodisplayer.features.photos.data.repositories

import com.example.photodisplayer.common.network.ApiHandler
import com.example.photodisplayer.features.photos.data.datasources.api.MarvelApiService
import com.example.photodisplayer.features.photos.data.datasources.database.PhotosDao
import com.example.photodisplayer.features.photos.data.mappers.MarvelCharacterDataMapper
import com.example.photodisplayer.features.photos.domain.entities.MarvelCharacter


class ImageRepository(
    private val marvelApiService: MarvelApiService,
    private val photosDao: PhotosDao
) : ApiHandler() {

    suspend fun getMarvelCharacters() = handle(marvelApiService::getMarvelCharacters) {
        MarvelCharacterDataMapper.toEntities(
            marvelCharacterDataModels = it.marvelData.results
        )
    }

    suspend fun addPhotos(marvelCharacters: List<MarvelCharacter>) {
        val databaseEntities = MarvelCharacterDataMapper.toDatabaseEntity(marvelCharacters = marvelCharacters)
        photosDao.addPhotos(databaseEntities)
    }

    suspend fun updatePhoto(marvelCharacter: MarvelCharacter) {
        val databaseEntity = MarvelCharacterDataMapper.toDatabaseEntity(marvelCharacter)
        photosDao.updatePhoto(databaseEntity)
    }

    suspend fun getSavedPhotos() : List<MarvelCharacter> {
        val databaseEntities = photosDao.getPhotos()
        return MarvelCharacterDataMapper.fromDatabaseEntities(databaseEntities)
    }

    suspend fun getPhotoById(id: String): MarvelCharacter {
        val databaseEntity = photosDao.getById(id)
        return MarvelCharacterDataMapper.fromDatabaseEntity(databaseEntity)
    }
}