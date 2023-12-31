package com.example.photodisplayer.features.photos.presentation.viewmodel


import com.example.photodisplayer.features.photos.presentation.viewmodel.viewentity.MarvelCharacterViewEntity

data class PhotosScreenState(
    val photos: List<MarvelCharacterViewEntity> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val filteredPhotos: List<MarvelCharacterViewEntity> = emptyList(),
    val query: String = ""
)