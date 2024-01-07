package com.example.photodisplayer.features.photodetails.domain.usecases

import com.example.photodisplayer.common.network.ApiResponse
import com.example.photodisplayer.features.photodetails.data.models.apiresponse.ShrinkApiResponse

interface CompressImageUsecase {

    suspend fun execute(imageUrl: String): ApiResponse<ShrinkApiResponse>

}