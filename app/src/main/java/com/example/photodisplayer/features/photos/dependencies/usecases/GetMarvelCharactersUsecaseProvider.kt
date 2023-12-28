package com.example.photodisplayer.features.photos.dependencies.usecases

import com.example.photodisplayer.features.photos.domain.usecasesimpl.GetMarvelCharactersUsecaseImpl
import com.example.photodisplayer.features.photos.dependencies.repositories.ImageRepositoryProvider
import com.example.photodisplayer.features.photos.domain.usecases.GetMarvelCharactersUsecase

class GetMarvelCharactersUsecaseProvider private constructor() {

    companion object {
        fun getMarvelCharactersUsecase(): GetMarvelCharactersUsecase {
            return GetMarvelCharactersUsecaseImpl(
                imageRepository = ImageRepositoryProvider.getImageRepositoryProvider()
            )
        }
    }

}