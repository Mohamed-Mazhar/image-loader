package com.example.photodisplayer.features.photodetails.domain.usecasesimpl

import android.util.Log
import com.example.photodisplayer.common.network.ApiResponse
import com.example.photodisplayer.features.photodetails.data.models.apiresponse.ShrinkApiResponse
import com.example.photodisplayer.features.photodetails.data.repositories.TinifyRepository
import com.example.photodisplayer.features.photodetails.domain.usecases.CompressImageUsecase

class CompressImageUsecaseImpl(
    private val tinifyRepository: TinifyRepository
): CompressImageUsecase {
    override suspend fun execute(imageUrl: String): ApiResponse<ShrinkApiResponse> {
        Log.d("Tinify", "Compressing usecase")
        return tinifyRepository.compress(imageUrl)
    }
}