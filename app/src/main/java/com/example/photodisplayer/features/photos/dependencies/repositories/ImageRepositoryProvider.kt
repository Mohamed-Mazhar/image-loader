package com.example.photodisplayer.features.photos.dependencies.repositories


import com.example.photodisplayer.common.database.AppDatabase
import com.example.photodisplayer.common.network.ApiService
import com.example.photodisplayer.features.photos.data.repositories.ImageRepository

class ImageRepositoryProvider private constructor() {

    companion object {
        fun get(): ImageRepository {
            return ImageRepository(
                marvelApiService = ApiService.marvelApiService,
                photosDao = AppDatabase.getPhotosDao()
            )
        }
    }

}