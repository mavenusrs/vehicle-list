package com.mavenusrs.vehicles.data.entities

import com.mavenusrs.vehicles.common.Mapper
import com.mavenusrs.vehicles.domain.model.Image

data class ImageEntity(
    val url: String?,
) : Mapper<Image> {
    override fun mapTo(): Image {
        return Image(url)
    }

}