package com.mavenusrs.vehicles.domain.usecase.vehicles

import com.mavenusrs.vehicles.common.Resource
import com.mavenusrs.vehicles.domain.model.Vehicle
import kotlinx.coroutines.flow.Flow

interface VehicleRepository {

    suspend fun getVehicles(): Flow<Resource<List<Vehicle>?>>
}
