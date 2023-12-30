package com.example.photodisplayer

import android.app.Application
import com.example.photodisplayer.common.database.AppDatabase

class PhotoDisplayerAppClass: Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabase.initializeDatabase(applicationContext)
    }

}