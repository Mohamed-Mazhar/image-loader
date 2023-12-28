package com.example.photodisplayer.features.photos.dependencies.repositories


import com.example.photodisplayer.common.network.ApiService
import com.example.photodisplayer.features.photos.data.repositories.ImageRepository

class ImageRepositoryProvider private constructor() {

    companion object {
        fun getImageRepositoryProvider(): ImageRepository {
            return ImageRepository(
                marvelApiService = ApiService.marvelApiService
            )
        }
    }

}