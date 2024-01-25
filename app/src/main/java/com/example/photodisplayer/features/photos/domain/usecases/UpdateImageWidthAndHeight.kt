package com.example.photodisplayer.features.photos.domain.usecases

interface UpdateImageWidthAndHeight {

    suspend fun execute(photoId: String, width: Int, height: Int)

}
