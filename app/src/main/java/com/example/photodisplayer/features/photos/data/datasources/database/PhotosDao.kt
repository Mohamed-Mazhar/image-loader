package com.example.photodisplayer.features.photos.data.datasources.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PhotosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPhotos(photoDatabaseEntity: List<PhotoDatabaseEntity>)

    @Update
    suspend fun updatePhoto(photoDatabaseEntity: PhotoDatabaseEntity)

    @Query("SELECT * FROM photos")
    suspend fun getPhotos(): List<PhotoDatabaseEntity>

    @Query("SELECT * FROM photos WHERE id =:id")
    suspend fun getById(id: String): PhotoDatabaseEntity
}