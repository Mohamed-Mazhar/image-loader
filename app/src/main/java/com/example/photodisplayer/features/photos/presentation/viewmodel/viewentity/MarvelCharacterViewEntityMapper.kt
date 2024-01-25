package com.example.photodisplayer.features.photos.presentation.viewmodel.viewentity

import com.example.photodisplayer.features.photos.domain.entities.MarvelCharacter

object MarvelCharacterViewEntityMapper {

    fun toViewEntity(marvelCharacter: MarvelCharacter): MarvelCharacterViewEntity {
        return MarvelCharacterViewEntity(
            id = marvelCharacter.id,
            name = marvelCharacter.name,
            imagePath = marvelCharacter.imagePath,
            caption = marvelCharacter.caption,
            ellipsizedCaption = getEllipsizedText(marvelCharacter.caption),
            width = marvelCharacter.width,
            height = marvelCharacter.height
        )
    }

    private fun getEllipsizedText(caption: String): String {
        return if (caption.trim().length >= 80) {
            caption.substring(0, 80).run {
                "$this..."
            }
        } else caption
    }

}