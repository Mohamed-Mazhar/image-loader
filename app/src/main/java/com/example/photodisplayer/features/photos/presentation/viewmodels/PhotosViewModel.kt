package com.example.photodisplayer.features.photos.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.photodisplayer.common.network.ApiResponse
import com.example.photodisplayer.features.photos.dependencies.usecases.GetMarvelCharactersUsecaseProvider
import com.example.photodisplayer.features.photos.domain.usecases.GetMarvelCharactersUsecase
import com.example.photodisplayer.features.photos.presentation.viewmodels.viewentity.MarvelCharacterViewEntityMapper
import kotlinx.coroutines.launch
import java.util.Locale

class PhotosViewModel(
    private val getPhotosUsecase: GetMarvelCharactersUsecase
) : ViewModel() {

    var state by mutableStateOf(PhotosScreenState())
        private set

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val getPhotosUsecase = GetMarvelCharactersUsecaseProvider.getMarvelCharactersUsecase()
                PhotosViewModel(
                    getPhotosUsecase = getPhotosUsecase,
                )
            }
        }
    }

    fun onEvent(event: PhotosScreenEvent) {
        when (event) {
            is PhotosScreenEvent.SearchFieldChangedEvent -> {
                updateFilteredList(query = event.text)
            }

            is PhotosScreenEvent.RefreshPageEvent -> {
                loadPhotos(isRefresh = true)
            }

            is PhotosScreenEvent.ScreenLaunchedEvent -> loadPhotos()
        }
    }

    private fun loadPhotos(isRefresh: Boolean = false) {
        viewModelScope.launch {
            state = state.copy(isLoading = isRefresh)
            state = when (val response = getPhotosUsecase.get()) {
                is ApiResponse.Error -> {
                    Log.d("Marvel", "Error receive ${response.exception}")
                    state.copy(isLoading = false, errorMessage = response.exception.message ?: "")
                }

                is ApiResponse.Success -> {
                    Log.d("Marvel", "Photos from marvel ${response.data}")
                    val viewEntities = response.data.map {
                        MarvelCharacterViewEntityMapper.toViewEntity(it)
                    }
                    state.copy(photos = viewEntities, isLoading = false, filteredPhotos = viewEntities)
                }
            }
        }
    }

    private fun updateFilteredList(query: String) {
        val filteredPhotos = if (query.isBlank()) {
            state.photos
        } else {
            state.photos.filter {
                it.caption.lowercase(Locale.getDefault()).contains(query.lowercase(Locale.getDefault()))
            }
        }
        state = state.copy(filteredPhotos = filteredPhotos, query = query)
    }

}