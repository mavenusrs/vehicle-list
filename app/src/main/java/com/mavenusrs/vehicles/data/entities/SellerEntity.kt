package com.mavenusrs.vehicles.data.entities

import com.mavenusrs.vehicles.common.Mapper
import com.mavenusrs.vehicles.domain.model.Seller

data class SellerEntity(
    val city: String?,
    val phone: String?,
    val type: String?,
): Mapper<Seller> {
    override fun mapTo(): Seller {
        return Seller(
            city,
            phone,
            type,
        )
    }

}