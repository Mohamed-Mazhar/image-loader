package com.example.photodisplayer.features.photos.presentation.viewmodels.viewentity

import com.example.photodisplayer.features.photos.domain.entities.MarvelCharacter

object MarvelCharacterViewEntityMapper {

    fun toViewEntity(marvelCharacter: MarvelCharacter): MarvelCharacterViewEntity {
        return MarvelCharacterViewEntity(
            id = marvelCharacter.id,
            name = marvelCharacter.name,
            imagePath = marvelCharacter.imagePath,
            caption = marvelCharacter.caption,
            ellipsizedCaption = getEllipsizedText(marvelCharacter.caption)
        )
    }

    private fun getEllipsizedText(caption: String): String {
        return if (caption.trim().length >= 80) {
            caption.substring(0, 80).run {
                "$this..."
            }
        } else if (caption.isBlank()) {
            "No Caption"
        } else {
            caption
        }
    }

}