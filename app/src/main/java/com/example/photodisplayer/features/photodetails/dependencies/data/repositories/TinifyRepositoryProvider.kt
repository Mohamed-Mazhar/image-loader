package com.example.photodisplayer.features.photodetails.dependencies.data.repositories

import com.example.photodisplayer.common.network.TinifyApiService
import com.example.photodisplayer.features.photodetails.data.repositories.TinifyRepository

class TinifyRepositoryProvider private constructor(){

    companion object {
        fun get() = TinifyRepository(tinifyWebService = TinifyApiService.tinifyApiService)
    }

}