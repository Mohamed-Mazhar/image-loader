package com.example.photodisplayer.features.photodetails.dependencies.domain

import com.example.photodisplayer.features.photodetails.domain.usecasesimpl.UpdatePhotoDetailsUsecaseImpl
import com.example.photodisplayer.features.photos.dependencies.repositories.ImageRepositoryProvider

class UpdatePhotoDetailsUsecaseProvider private constructor(){

    companion object {
        fun get() = UpdatePhotoDetailsUsecaseImpl(imageRepository = ImageRepositoryProvider.get())
    }

}