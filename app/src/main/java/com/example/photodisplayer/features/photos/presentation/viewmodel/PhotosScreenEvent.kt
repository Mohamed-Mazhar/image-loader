package com.example.photodisplayer.features.photos.presentation.viewmodel

sealed class PhotosScreenEvent {
    data object ScreenLaunchedEvent : PhotosScreenEvent()
    data object RefreshPageEvent : PhotosScreenEvent()
    data class SearchFieldChangedEvent(val text: String) : PhotosScreenEvent()
    data class UpdatePhotoDetails(val photoId: String, val width: Int, val height: Int): PhotosScreenEvent()
    data object DismissErrorDialog: PhotosScreenEvent()
}