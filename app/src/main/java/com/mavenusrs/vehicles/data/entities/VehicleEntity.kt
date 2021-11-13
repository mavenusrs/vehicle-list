package com.mavenusrs.vehicles.data.entities

import com.mavenusrs.vehicles.common.Mapper
import com.mavenusrs.vehicles.domain.model.Vehicle

data class VehicleEntity(
    val colour: String?,
    val description: String?,
    val firstRegistration: String?,
    val fuel: String?,
    val id: Int?,
    val images: List<ImageEntity>?,
    val make: String?,
    val mileage: Int?,
    val model: String?,
    val modelline: String?,
    val price: Int?,
    val seller: SellerEntity?,
) : Mapper<Vehicle> {
    override fun mapTo(): Vehicle {
        return Vehicle(
            colour,
            description,
            firstRegistration,
            fuel,
            id,
            images?.map {
                it.mapTo()
            },
            make,
            mileage,
            model,
            modelline,
            price,
            seller?.mapTo(),
        )
    }

}