package com.mavenusrs.vehicles.data.repository

import com.google.common.truth.Truth
import com.mavenusrs.common.UnitTest
import com.mavenusrs.vehicles.common.Resource
import com.mavenusrs.vehicles.data.entities.VehicleEntity
import com.mavenusrs.vehicles.data.remote.APIService
import com.mavenusrs.vehicles.domain.model.Vehicle
import com.mavenusrs.vehicles.domain.usecase.vehicles.VehicleRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class VehicleRepositoryImplTest : UnitTest() {
    private lateinit var vehicleRepository: VehicleRepository

    @MockK
    private lateinit var apiService: APIService

    @MockK
    private lateinit var vehicleResponse: Response<List<VehicleEntity?>?>

    @Before
    fun setup() {
        vehicleRepository = VehicleRepositoryImpl(apiService)
    }

    @Test
    fun `check return data Error if getVehicle return null`() = runBlockingTest {
        every { vehicleResponse.body() } returns null
        every { vehicleResponse.isSuccessful } returns true
        coEvery { apiService.getVehicles() } returns vehicleResponse

        val vehicles = vehicleRepository.getVehicles()
        vehicles.collect {
            Truth.assertThat(it).isEqualTo(
                Resource.Failed<List<Vehicle>>(
                    null,
                    failureType = Resource.FailureType.DATA_FAILURE
                )
            )
        }
    }

    @Test
    fun `check return server Error if response is Unsuccessful`() = runBlockingTest {
        every { vehicleResponse.errorBody()?.toString() } returns ""
        every { vehicleResponse.isSuccessful } returns false
        coEvery { apiService.getVehicles() } returns vehicleResponse

        val vehicles = vehicleRepository.getVehicles()
        vehicles.collect {
            Truth.assertThat(it).isInstanceOf(Resource.Failed::class.java)
            Truth.assertThat((it as Resource.Failed).failureType).isEqualTo(
                Resource.FailureType.SERVER_FAILURE
            )
        }
    }

    @Test
    fun `check getVehicle return successfully`() = runBlockingTest {
        val vehicleEntity = VehicleEntity(
            "White",
            "dsf",
            "02-2008",
            "Gasoline",
            2,
            null,
            "Audi",
            0,
            "A8",
            "quattro",
            16000, null
        )
        val vehicleEntityList = listOf(vehicleEntity)

        every { vehicleResponse.body() } returns vehicleEntityList
        every { vehicleResponse.isSuccessful } returns true
        coEvery { apiService.getVehicles() } returns vehicleResponse

        val vehicles = vehicleRepository.getVehicles()
        vehicles.collect { resource ->
            Truth.assertThat(resource).isEqualTo(
                Resource.Success(
                    vehicleEntityList.map { vehicle ->
                        vehicle.mapTo()
                    }
                )
            )
        }
    }

}