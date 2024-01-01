package com.example.photodisplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.photodisplayer.common.theme.PhotoDisplayerTheme
import com.example.photodisplayer.common.ui.Screen
import com.example.photodisplayer.features.photodetails.presentation.screen.PhotoDetailsScreen
import com.example.photodisplayer.features.photodetails.presentation.viewmodel.PhotoDetailsViewModel
import com.example.photodisplayer.features.photos.presentation.screen.PhotoListScreen
import com.example.photodisplayer.features.photos.presentation.viewmodel.PhotosViewModel

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
                        PhotoListScreen(
                            navController = navController,
                        )
                    }
                    composable(
                        route = Screen.PhotoDetailsScreen.route,
                    ) {
                        val id = it.arguments?.getString("id")
                        id?.let { photoId ->
                            PhotoDetailsScreen(
                                navController = navController,
                                id = photoId,
                            )
                        }
                    }
                }
            }
        }
    }
}