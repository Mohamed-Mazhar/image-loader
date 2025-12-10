package com.example.photodisplayer.features.photos.presentation.viewmodel.viewentity

import com.example.photodisplayer.features.photos.domain.entities.Image

object PhotoViewEntityMapper {

    fun toViewEntity(image: Image): PhotoViewEntity {
        return PhotoViewEntity(
            id = image.id,
            name = image.name,
            imagePath = image.imagePath,
            caption = image.caption,
            ellipsizedCaption = getEllipsizedText(image.caption),
            width = image.width,
            height = image.height
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