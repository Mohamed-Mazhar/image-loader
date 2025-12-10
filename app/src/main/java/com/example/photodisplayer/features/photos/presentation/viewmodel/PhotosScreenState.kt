package com.example.photodisplayer.features.photos.presentation.viewmodel


import com.example.photodisplayer.features.photos.presentation.viewmodel.viewentity.PhotoViewEntity

data class PhotosScreenState(
    val photos: List<PhotoViewEntity> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val filteredPhotos: List<PhotoViewEntity> = emptyList(),
    val query: String = ""
)