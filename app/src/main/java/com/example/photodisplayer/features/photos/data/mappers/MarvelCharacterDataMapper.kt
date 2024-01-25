package com.example.photodisplayer.features.photos.data.mappers

import com.example.photodisplayer.common.util.toHttps
import com.example.photodisplayer.features.photos.data.datasources.database.PhotoDatabaseEntity
import com.example.photodisplayer.features.photos.data.models.MarvelCharacterDataModel
import com.example.photodisplayer.features.photos.domain.entities.MarvelCharacter

object MarvelCharacterDataMapper {

    fun toEntities(marvelCharacterDataModels: List<MarvelCharacterDataModel>): List<MarvelCharacter> {
        return marvelCharacterDataModels.map {
            MarvelCharacter(
                id = it.id,
                name = it.name,
                caption = it.description,
                imagePath = "${it.marvelCharacterImage.path.toHttps()}.${it.marvelCharacterImage.extension}"
            )
        }
    }

    fun fromDatabaseEntities(databaseEntities: List<PhotoDatabaseEntity>): List<MarvelCharacter> {
        return databaseEntities.map {
            fromDatabaseEntity(it)
        }
    }

    fun fromDatabaseEntity(databaseEntity: PhotoDatabaseEntity) = MarvelCharacter(
        id = databaseEntity.id,
        name = databaseEntity.name,
        caption = databaseEntity.description,
        imagePath = databaseEntity.url,
        height = databaseEntity.height,
        width = databaseEntity.width
    )

    fun toDatabaseEntity(marvelCharacters: List<MarvelCharacter>): List<PhotoDatabaseEntity> {
        return marvelCharacters.map {
            toDatabaseEntity(marvelCharacter = it)
        }
    }

    fun toDatabaseEntity(marvelCharacter: MarvelCharacter): PhotoDatabaseEntity {
        return PhotoDatabaseEntity(
            id = marvelCharacter.id,
            name = marvelCharacter.name,
            url = marvelCharacter.imagePath,
            description = marvelCharacter.caption,
            height = marvelCharacter.height,
            width = marvelCharacter.width,
        )
    }

}