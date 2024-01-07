package com.example.photodisplayer.features.photos.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.photodisplayer.common.network.WebserviceException
import com.example.photodisplayer.features.photos.dependencies.usecases.GetMarvelCharactersUsecaseProvider
import com.example.photodisplayer.features.photos.dependencies.usecases.RefreshMarvelCharactersUsecaseProvider
import com.example.photodisplayer.features.photos.domain.usecases.GetMarvelCharactersUsecase
import com.example.photodisplayer.features.photos.domain.usecases.RefreshMarvelCharactersUsecase
import com.example.photodisplayer.features.photos.presentation.viewmodel.viewentity.MarvelCharacterViewEntityMapper
import kotlinx.coroutines.launch
import java.util.Locale

class PhotosViewModel(
    private val getPhotosUsecase: GetMarvelCharactersUsecase,
    private val refreshMarvelCharactersUsecase: RefreshMarvelCharactersUsecase,
) : ViewModel() {

    var state by mutableStateOf(PhotosScreenState())
        private set

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val getPhotosUsecase = GetMarvelCharactersUsecaseProvider.get()
                val refreshMarvelCharactersUsecase = RefreshMarvelCharactersUsecaseProvider.get()
                PhotosViewModel(
                    getPhotosUsecase = getPhotosUsecase,
                    refreshMarvelCharactersUsecase = refreshMarvelCharactersUsecase
                )
            }
        }
    }

    fun onEvent(event: PhotosScreenEvent) {
        viewModelScope.launch {
            when (event) {
                is PhotosScreenEvent.SearchFieldChangedEvent -> {
                    updateFilteredList(query = event.text)
                }

                is PhotosScreenEvent.RefreshPageEvent -> {
                    refreshCharacters()
                }

                is PhotosScreenEvent.ScreenLaunchedEvent -> loadPhotos()
                is PhotosScreenEvent.DismissErrorDialog -> state = state.copy(errorMessage = "")
            }
        }
    }

    private suspend fun loadPhotos() {
        val photos = getPhotosUsecase.execute()
        if (photos.isEmpty()) {
            refreshCharacters()
        } else {
            val viewEntities = photos.map {
                MarvelCharacterViewEntityMapper.toViewEntity(it)
            }
            state = state.copy(photos = viewEntities, isLoading = false, filteredPhotos = viewEntities)
        }
    }

    private suspend fun refreshCharacters() {
        state = state.copy(isLoading = true)
        state = try {
            refreshMarvelCharactersUsecase.execute()
            val photos = getPhotosUsecase.execute()
            val viewEntities = photos.map {
                MarvelCharacterViewEntityMapper.toViewEntity(it)
            }
            state.copy(photos = viewEntities, isLoading = false, filteredPhotos = viewEntities)
        } catch (e: WebserviceException) {
            state.copy(errorMessage = e.errorMessage, isLoading = false)
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