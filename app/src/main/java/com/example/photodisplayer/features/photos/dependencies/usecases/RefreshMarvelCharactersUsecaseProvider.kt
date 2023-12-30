package com.example.photodisplayer.features.photos.dependencies.usecases

import com.example.photodisplayer.features.photos.dependencies.repositories.ImageRepositoryProvider
import com.example.photodisplayer.features.photos.domain.usecases.RefreshMarvelCharactersUsecase
import com.example.photodisplayer.features.photos.domain.usecasesimpl.RefreshMarvelCharactersUsecaseImpl

class RefreshMarvelCharactersUsecaseProvider private constructor() {

    companion object {
        fun get(): RefreshMarvelCharactersUsecase {
            return RefreshMarvelCharactersUsecaseImpl(
                imageRepository = ImageRepositoryProvider.get()
            )
        }
    }

}