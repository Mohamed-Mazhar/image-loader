package com.example.photodisplayer.features.photodetails.domain.usecases

import com.example.photodisplayer.features.photos.domain.entities.MarvelCharacter

interface GetPhotoByIdUsecase {
    suspend fun execute(id: String): MarvelCharacter
}