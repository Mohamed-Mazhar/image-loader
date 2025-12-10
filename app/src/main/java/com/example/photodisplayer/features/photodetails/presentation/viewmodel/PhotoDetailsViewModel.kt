package com.example.photodisplayer.features.photodetails.presentation.viewmodel


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.photodisplayer.features.photodetails.dependencies.domain.CompressImageUsecaseProvider
import com.example.photodisplayer.features.photodetails.dependencies.domain.GetPhotoByIdUsecaseProvider
import com.example.photodisplayer.features.photodetails.dependencies.domain.UpdatePhotoDetailsUsecaseProvider
import com.example.photodisplayer.features.photodetails.domain.usecases.CompressImageUsecase
import com.example.photodisplayer.features.photodetails.domain.usecases.GetPhotoByIdUsecase
import com.example.photodisplayer.features.photodetails.domain.usecases.UpdatePhotoDetailsUsecase
import com.example.photodisplayer.features.photodetails.presentation.viewmodel.PhotoDetailsEvent.CaptionChanged
import com.example.photodisplayer.features.photodetails.presentation.viewmodel.PhotoDetailsEvent.ScreenLaunched
import com.example.photodisplayer.features.photodetails.presentation.viewmodel.PhotoDetailsEvent.UpdatePhotoDetails
import kotlinx.coroutines.launch

class PhotoDetailsViewModel(
    private val getPhotoByIdUsecase: GetPhotoByIdUsecase,
    private val updatePhotoDetailsUsecase: UpdatePhotoDetailsUsecase,
    private val compressImageUsecase: CompressImageUsecase,
) : ViewModel() {

    var state by mutableStateOf(PhotoDetailsState())
        private set

    private var aspectRatio: Double = 1.0

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                PhotoDetailsViewModel(
                    getPhotoByIdUsecase = GetPhotoByIdUsecaseProvider.get(),
                    updatePhotoDetailsUsecase = UpdatePhotoDetailsUsecaseProvider.get(),
                    compressImageUsecase = CompressImageUsecaseProvider.get(),
                )
            }
        }
    }

    fun onEvent(event: PhotoDetailsEvent) {
        viewModelScope.launch {
            when (event) {
                is ScreenLaunched -> getPhotoDetails(id = event.id)
                is CaptionChanged -> updatePhotoCaption(caption = event.text)
                is UpdatePhotoDetails -> updatePhoto()
                is PhotoDetailsEvent.CompressPhoto -> compressImage()
                is PhotoDetailsEvent.UpdatePhotoWidth -> updateWidth(event.width.toIntOrNull() ?: 0)
                is PhotoDetailsEvent.UpdatePhotoHeight -> updateHeight(event.height.toIntOrNull() ?: 0)
            }
        }
    }

    private fun updateWidth(width: Int) {
        state.image?.let {
            state = state.copy(
                image = it.copy(
                    width = width,
                    height = width.times(1 / aspectRatio).toInt()
                )
            )
        }
    }

    private fun updateHeight(height: Int) {
        state.image?.let {
            state = state.copy(
                image = it.copy(
                    height = height,
                    width = height.times(aspectRatio).toInt()
                )
            )
        }
    }

    private suspend fun compressImage() {
        state.image?.imagePath?.let {
            state = state.copy(isLoading = true)
            val compressedUrl = compressImageUsecase.execute(imageUrl = it)
            state = state.copy(isLoading = false)
            Log.d("Tinify", "Compressed url $compressedUrl")
        }
    }

    private suspend fun updatePhoto() {
        state.image?.let {
            updatePhotoDetailsUsecase.execute(it)
        }
        state = state.copy(updateCompleted = true)
    }

    private fun updatePhotoCaption(caption: String) {
        state = state.copy(
            image = state.image?.copy(
                caption = caption
            )
        )
    }

    private suspend fun getPhotoDetails(id: String) {
        val photoDetails = getPhotoByIdUsecase.execute(id)
        state = state.copy(image = photoDetails)
        if (photoDetails.height != null && photoDetails.width != null) {
            aspectRatio = (photoDetails.width.toDouble() / photoDetails.height)
        }
    }

}