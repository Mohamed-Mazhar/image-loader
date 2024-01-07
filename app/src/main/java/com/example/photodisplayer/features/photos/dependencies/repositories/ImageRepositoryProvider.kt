package com.example.photodisplayer.features.photos.dependencies.repositories


import com.example.photodisplayer.common.database.AppDatabase
import com.example.photodisplayer.common.network.MarvelApiService
import com.example.photodisplayer.features.photos.data.repositories.ImageRepository

class ImageRepositoryProvider private constructor() {

    companion object {
        fun get(): ImageRepository {
            return ImageRepository(
                marvelWebService = MarvelApiService.marvelWebService,
                photosDao = AppDatabase.getPhotosDao()
            )
        }
    }

}