package com.example.photodisplayer.features.photos.data.mappers

import com.example.imageloader.common.util.toHttps
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

}