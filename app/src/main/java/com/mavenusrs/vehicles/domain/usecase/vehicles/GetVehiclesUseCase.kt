package com.mavenusrs.vehicles.domain.usecase.vehicles

import com.mavenusrs.vehicles.common.BaseUseCase
import com.mavenusrs.vehicles.common.Resource
import com.mavenusrs.vehicles.domain.model.Vehicle
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class GetVehiclesUseCase @Inject constructor(private val vehiclesRepository: VehicleRepository) :
    BaseUseCase<Unit, List<Vehicle>?>() {

    override suspend fun run(param: Unit): Flow<Resource<List<Vehicle>?>> {
        return vehiclesRepository.getVehicles()
    }

}