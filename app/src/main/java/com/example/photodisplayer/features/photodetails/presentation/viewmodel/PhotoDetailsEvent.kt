package com.example.photodisplayer.features.photodetails.presentation.viewmodel

sealed class PhotoDetailsEvent {
    data class ScreenLaunched(val id: String) : PhotoDetailsEvent()
    data class CaptionChanged(val text: String) : PhotoDetailsEvent()
    data object UpdatePhotoDetails : PhotoDetailsEvent()
    data object CompressPhoto : PhotoDetailsEvent()
    data class UpdatePhotoWidth(val width: String): PhotoDetailsEvent()
    data class UpdatePhotoHeight(val height: String): PhotoDetailsEvent()

}