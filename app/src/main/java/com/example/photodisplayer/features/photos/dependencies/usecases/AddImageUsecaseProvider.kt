package com.example.photodisplayer.features.photos.dependencies.usecases

import com.example.photodisplayer.features.photos.dependencies.repositories.ImageRepositoryProvider
import com.example.photodisplayer.features.photos.domain.usecases.AddImageUsecase
import com.example.photodisplayer.features.photos.domain.usecasesimpl.AddImageUsecaseImpl

class AddImageUsecaseProvider private constructor() {

    companion object {
        fun get(): AddImageUsecase {
            return AddImageUsecaseImpl(
                imageRepository = ImageRepositoryProvider.get()
            );
        }
    }

}