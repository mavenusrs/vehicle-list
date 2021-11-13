package com.mavenusrs.vehicles.data.repository

import com.mavenusrs.vehicles.common.Resource
import com.mavenusrs.vehicles.data.remote.APIService
import com.mavenusrs.vehicles.domain.model.Vehicle
import com.mavenusrs.vehicles.domain.usecase.vehicles.VehicleRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class VehicleRepositoryImpl @Inject constructor(private val apiService: APIService) :
    VehicleRepository {

    override suspend fun getVehicles(): Flow<Resource<List<Vehicle?>?>> = flow {
        val response = apiService.getVehicles()
        emit(when (response.isSuccessful) {
            true -> {
                response.body()?.let {
                    Resource.Success(it.map { vehicle ->
                        vehicle?.mapTo()
                    })
                } ?: Resource.Failed(failureType = Resource.FailureType.DATA_FAILURE)
            }
            false -> Resource.Failed(
                Throwable(response.errorBody()?.toString()),
                Resource.FailureType.SERVER_FAILURE
            )
        }
        )
    }

}