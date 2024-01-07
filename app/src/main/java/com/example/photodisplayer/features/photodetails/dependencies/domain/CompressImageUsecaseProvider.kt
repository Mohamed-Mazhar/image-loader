package com.example.photodisplayer.features.photodetails.dependencies.domain

import com.example.photodisplayer.features.photodetails.dependencies.data.repositories.TinifyRepositoryProvider
import com.example.photodisplayer.features.photodetails.domain.usecases.CompressImageUsecase
import com.example.photodisplayer.features.photodetails.domain.usecasesimpl.CompressImageUsecaseImpl

class CompressImageUsecaseProvider private constructor() {

    companion object {
        fun get(): CompressImageUsecase {
            return CompressImageUsecaseImpl(tinifyRepository = TinifyRepositoryProvider.get())
        }
    }

}