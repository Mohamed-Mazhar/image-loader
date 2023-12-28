package com.example.photodisplayer.common.network

class WebserviceException(val code: Int, val description: String, val errorMessage: String) : Exception()