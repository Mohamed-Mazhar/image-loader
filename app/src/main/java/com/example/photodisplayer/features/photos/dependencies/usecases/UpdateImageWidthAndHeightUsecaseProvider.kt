package com.example.photodisplayer.features.photos.dependencies.usecases

import com.example.photodisplayer.features.photos.dependencies.repositories.ImageRepositoryProvider
import com.example.photodisplayer.features.photos.domain.usecases.UpdateImageWidthAndHeight
import com.example.photodisplayer.features.photos.domain.usecasesimpl.UpdateImageWidthAndHeightImpl

class UpdateImageWidthAndHeightUsecaseProvider private constructor() {

    companion object {
        fun get(): UpdateImageWidthAndHeight {
            return UpdateImageWidthAndHeightImpl(
                imageRepository = ImageRepositoryProvider.get()
            )
        }
    }

}