package com.example.photodisplayer.features.photos.data.mappers

import com.example.photodisplayer.common.util.toHttps
import com.example.photodisplayer.features.photos.data.datasources.database.PhotoDatabaseEntity
import com.example.photodisplayer.features.photos.data.models.MarvelCharacterDataModel
import com.example.photodisplayer.features.photos.domain.entities.Image

object ImageDataMapper {

    fun toEntities(marvelCharacterDataModels: List<MarvelCharacterDataModel>): List<Image> {
        return marvelCharacterDataModels.map {
            Image(
                id = it.id,
                name = it.name,
                caption = it.description,
                imagePath = "${it.marvelCharacterImage.path.toHttps()}.${it.marvelCharacterImage.extension}"
            )
        }
    }

    fun fromDatabaseEntities(databaseEntities: List<PhotoDatabaseEntity>): List<Image> {
        return databaseEntities.map {
            fromDatabaseEntity(it)
        }
    }

    fun fromDatabaseEntity(databaseEntity: PhotoDatabaseEntity) = Image(
        id = databaseEntity.id,
        name = databaseEntity.name,
        caption = databaseEntity.description,
        imagePath = databaseEntity.url,
        height = databaseEntity.height,
        width = databaseEntity.width
    )

    fun toDatabaseEntity(images: List<Image>): List<PhotoDatabaseEntity> {
        return images.map {
            toDatabaseEntity(image = it)
        }
    }

    fun toDatabaseEntity(image: Image): PhotoDatabaseEntity {
        return PhotoDatabaseEntity(
            id = image.id,
            name = image.name,
            url = image.imagePath,
            description = image.caption,
            height = image.height,
            width = image.width,
        )
    }

}