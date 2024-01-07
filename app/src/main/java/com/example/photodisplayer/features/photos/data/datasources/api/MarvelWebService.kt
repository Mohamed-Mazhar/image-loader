package com.example.photodisplayer.features.photos.data.datasources.api

import com.example.photodisplayer.common.network.MarvelApiResponse
import com.example.photodisplayer.common.network.Urls
import com.example.photodisplayer.features.photos.data.models.MarvelCharacterDataModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.GET

interface MarvelWebService {

    @GET(Urls.MARVEL.CHARACTERS)
    suspend fun getMarvelCharacters(): Response<MarvelApiResponse<MarvelCharacterDataModel>>

}