package com.example.photodisplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.photodisplayer.common.ui.Screen
import com.example.photodisplayer.features.photos.presentation.screens.PhotoListScreen
import com.example.photodisplayer.features.photos.presentation.viewmodels.PhotosScreenEvent
import com.example.photodisplayer.features.photos.presentation.viewmodels.PhotosViewModel
import com.example.photodisplayer.common.theme.PhotoDisplayerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotoDisplayerTheme {
                val keyboardController = LocalSoftwareKeyboardController.current
                val focusManager = LocalFocusManager.current
                val interactionSource = remember { MutableInteractionSource() }
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.PhotoListScreen.route,
                    modifier = Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        keyboardController?.hide()
                        focusManager.clearFocus(true)
                    },
                ) {
                    composable(Screen.PhotoListScreen.route) {
                        val viewModel: PhotosViewModel by viewModels { PhotosViewModel.Factory }
                        val screenState = viewModel.state
                        PhotoListScreen(
                            photosScreenState = screenState,
                            onScreenLaunched = {
                                viewModel.onEvent(event = PhotosScreenEvent.ScreenLaunchedEvent)
                            },
                            onRefreshPerformed = {
                                viewModel.onEvent(event = PhotosScreenEvent.RefreshPageEvent)
                            },
                            onQueryChanged = {
                                viewModel.onEvent(event = PhotosScreenEvent.SearchFieldChangedEvent(it))
                            }
                        )
                    }
                }
            }
        }
    }
}