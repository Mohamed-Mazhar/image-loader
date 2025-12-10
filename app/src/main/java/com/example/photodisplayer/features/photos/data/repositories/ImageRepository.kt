package com.example.photodisplayer.features.photos.data.repositories

import android.util.Log
import com.example.photodisplayer.common.network.ApiHandler
import com.example.photodisplayer.features.photos.data.datasources.api.MarvelWebService
import com.example.photodisplayer.features.photos.data.datasources.database.PhotosDao
import com.example.photodisplayer.features.photos.data.mappers.ImageDataMapper
import com.example.photodisplayer.features.photos.domain.entities.Image


class ImageRepository(
    private val marvelWebService: MarvelWebService,
    private val photosDao: PhotosDao
) : ApiHandler() {

    suspend fun getMarvelCharacters() = handleGetRequestResponse(
        marvelWebService::getMarvelCharacters
    ) {
        ImageDataMapper.toEntities(
            marvelCharacterDataModels = it.marvelData.results
        )
    }

    suspend fun addPhotos(images: List<Image>) {
        val databaseEntities = ImageDataMapper.toDatabaseEntity(images = images)
        photosDao.addPhotos(databaseEntities)
    }

    suspend fun updatePhoto(image: Image) {
        val databaseEntity = ImageDataMapper.toDatabaseEntity(image)
        photosDao.updatePhoto(databaseEntity)
    }

    suspend fun getSavedPhotos() : List<Image> {
        val databaseEntities = photosDao.getPhotos()
        return ImageDataMapper.fromDatabaseEntities(databaseEntities)
    }

    suspend fun getPhotoById(id: String): Image {
        val databaseEntity = photosDao.getById(id)
        Log.d("ImageRepository", "Retrieving image $id")
        return ImageDataMapper.fromDatabaseEntity(databaseEntity)
    }

    suspend fun addPhoto(image: Image) {
        val databaseEntity = ImageDataMapper.toDatabaseEntity(image = image)
        Log.d("ImageRepository", "Added image ${databaseEntity.id}")
        photosDao.addPhoto(databaseEntity)
    }
}