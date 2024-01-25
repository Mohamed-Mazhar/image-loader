package com.example.photodisplayer.features.photos.data.datasources.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class PhotoDatabaseEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id : String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "height")
    val height: Int?,
    @ColumnInfo(name = "width")
    val width: Int?

)