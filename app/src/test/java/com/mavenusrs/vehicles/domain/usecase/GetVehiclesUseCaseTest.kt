package com.mavenusrs.vehicles.domain.usecase

import com.mavenusrs.common.UnitTest
import com.mavenusrs.vehicles.common.Resource
import com.mavenusrs.vehicles.domain.model.Vehicle
import com.mavenusrs.vehicles.domain.usecase.vehicles.GetVehiclesUseCase
import com.mavenusrs.vehicles.domain.usecase.vehicles.VehicleRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetVehiclesUseCaseTest : UnitTest() {

    private lateinit var getVehiclesUseCase: GetVehiclesUseCase

    @MockK
    private lateinit var vehiclesRepository: VehicleRepository

    @Before
    fun setup() {
        getVehiclesUseCase = GetVehiclesUseCase(vehiclesRepository)
    }

    @Test
    fun `should call get vehicle from Repository only once`() = runBlockingTest {
        coEvery { vehiclesRepository.getVehicles() } returns flow {
            emit(Resource.Success(emptyList<Vehicle>()))
        }

        getVehiclesUseCase.run(Unit)
        coVerify(exactly = 1) {
            vehiclesRepository.getVehicles()
        }
    }

}