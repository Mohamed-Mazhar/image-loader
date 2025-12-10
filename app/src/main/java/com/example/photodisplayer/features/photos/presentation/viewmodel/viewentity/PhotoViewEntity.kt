package com.example.photodisplayer.features.photos.presentation.viewmodel.viewentity

data class PhotoViewEntity(
    val id: String,
    val name: String,
    val ellipsizedCaption: String,
    val caption: String,
    val imagePath: String,
    val height: Int?,
    val width: Int?
)