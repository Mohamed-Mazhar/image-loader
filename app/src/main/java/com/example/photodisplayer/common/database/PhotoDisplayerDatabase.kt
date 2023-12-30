package com.example.photodisplayer.common.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.photodisplayer.features.photos.data.datasources.database.PhotoDatabaseEntity
import com.example.photodisplayer.features.photos.data.datasources.database.PhotosDao

@Database(entities = [PhotoDatabaseEntity::class], version = 1)
abstract class PhotoDisplayerDatabase : RoomDatabase() {
    abstract fun photosDao(): PhotosDao
}

object AppDatabase {

    private lateinit var databaseInstance: PhotoDisplayerDatabase

    fun initializeDatabase(context: Context) {
        databaseInstance = Room.databaseBuilder(
            context, PhotoDisplayerDatabase::class.java, "photo_displayer"
        ).build()
    }

    fun getPhotosDao() = databaseInstance.photosDao()
}