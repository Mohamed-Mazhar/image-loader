package com.example.photodisplayer.common.ui

sealed class Screen(val route: String) {
    data object PhotoListScreen: Screen("photo_list")
}