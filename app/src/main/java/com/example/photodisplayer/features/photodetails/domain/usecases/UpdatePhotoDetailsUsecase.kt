package com.example.photodisplayer.features.photodetails.domain.usecases

import com.example.photodisplayer.features.photos.domain.entities.MarvelCharacter

interface UpdatePhotoDetailsUsecase {
    suspend fun execute(marvelCharacter: MarvelCharacter)
}