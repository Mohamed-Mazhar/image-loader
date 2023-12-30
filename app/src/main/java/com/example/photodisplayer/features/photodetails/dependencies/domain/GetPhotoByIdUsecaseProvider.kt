package com.example.photodisplayer.features.photodetails.dependencies.domain

import com.example.photodisplayer.features.photodetails.domain.usecases.GetPhotoByIdUsecase
import com.example.photodisplayer.features.photodetails.domain.usecasesimpl.GetPhotoByIdUsecaseImpl
import com.example.photodisplayer.features.photos.dependencies.repositories.ImageRepositoryProvider

class GetPhotoByIdUsecaseProvider private constructor(){

    companion object {
        fun get(): GetPhotoByIdUsecase {
            return GetPhotoByIdUsecaseImpl(
                imageRepository = ImageRepositoryProvider.get()
            )
        }
    }

}