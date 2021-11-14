package com.mavenusrs.vehicles.features.vehicle_list_feat

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth

import com.mavenusrs.common.UnitTest
import com.mavenusrs.common.getOrAwaitValue
import com.mavenusrs.vehicles.common.Resource
import com.mavenusrs.vehicles.domain.model.Note
import com.mavenusrs.vehicles.domain.model.Vehicle
import com.mavenusrs.vehicles.domain.usecase.notes.GetVehiclesNotesUseCase
import com.mavenusrs.vehicles.domain.usecase.vehicles.GetVehiclesUseCase
import com.mavenusrs.vehicles.features.common.StatefulResource
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class VehicleListViewModelTest : UnitTest() {

    @get:Rule
    val instanceTaskExecutorRuler = InstantTaskExecutorRule()

    @MockK
    private lateinit var getVehiclesUseCase: GetVehiclesUseCase

    @MockK
    private lateinit var getVehiclesNotesUseCase: GetVehiclesNotesUseCase

    private lateinit var vehicleListViewModel: VehicleListViewModel

    private var vehicles: List<Vehicle>? = null

    private var notes: List<Note>? = null

    @Before
    fun setup() {
        vehicleListViewModel = VehicleListViewModel(
            getVehiclesUseCase, getVehiclesNotesUseCase
        )
        val vehicle = Vehicle(
            "White",
            "dsf",
            "02-2008",
            "Gasoline",
            2,
            null,
            "Audi",
            0,
            "A8",
            "quarto",
            16000, null
        )
        vehicles = listOf(vehicle)

        val note1 = Note(
            "note 1", 3
        )

        val note2 = Note(
            "note 2", 2
        )
        notes = listOf(note1, note2)

    }

    @Test
    fun `get vehicles should return success`() {

        every { getVehiclesUseCase(any(), Unit, any()) }.answers {
            thirdArg<(Resource<List<Vehicle>?>) -> Unit>()(Resource.Success(vehicles))
        }

        vehicleListViewModel.getVehicles()

        val statefulResponse = vehicleListViewModel.vehicleLiveData.getOrAwaitValue()
        Truth.assertThat(statefulResponse.status).isEqualTo(StatefulResource.Status.SUCCESSES)
        Truth.assertThat(statefulResponse.errorMessage).isNull()
        Truth.assertThat(statefulResponse.status).isNotEqualTo(StatefulResource.Status.LOADING)
        Truth.assertThat(statefulResponse.status).isNotEqualTo(StatefulResource.Status.FAILED)

        Truth.assertThat(statefulResponse.data).isEqualTo(vehicles)
    }

    @Test
    fun `get vehicles should return empty`() {

        every { getVehiclesUseCase(any(), Unit, any()) }.answers {
            thirdArg<(Resource<List<Vehicle>?>) -> Unit>()(Resource.Success(emptyList()))
        }

        vehicleListViewModel.getVehicles()

        val statefulResponse = vehicleListViewModel.vehicleLiveData.getOrAwaitValue()
        Truth.assertThat(statefulResponse.status).isEqualTo(StatefulResource.Status.IS_EMPTY)
        Truth.assertThat(statefulResponse.errorMessage).isNull()
        Truth.assertThat(statefulResponse.status).isNotEqualTo(StatefulResource.Status.LOADING)
        Truth.assertThat(statefulResponse.status).isNotEqualTo(StatefulResource.Status.FAILED)

        Truth.assertThat(statefulResponse.data).isEqualTo(null)
    }

    @Test
    fun `get vehicles should return error`() {

        every { getVehiclesUseCase(any(), Unit, any()) }.answers {
            thirdArg<(Resource<List<Vehicle>?>) -> Unit>()(Resource.Failed(failureType = Resource.FailureType.SERVER_FAILURE))
        }

        vehicleListViewModel.getVehicles()

        val statefulResponse = vehicleListViewModel.vehicleLiveData.getOrAwaitValue()
        Truth.assertThat(statefulResponse.status).isEqualTo(StatefulResource.Status.FAILED)
        Truth.assertThat(statefulResponse.errorMessage).isNotNull()
        Truth.assertThat(statefulResponse.status).isNotEqualTo(StatefulResource.Status.LOADING)

        Truth.assertThat(statefulResponse.data).isEqualTo(null)
    }


    @Test
    fun `get vehicles and notes arr=e merged successfully`() {
        val note = "note 2"
        val vehicleId = 2
        every { getVehiclesUseCase(any(), Unit, any()) }.answers {
            thirdArg<(Resource<List<Vehicle>?>) -> Unit>()(Resource.Success(vehicles))
        }

        every { getVehiclesNotesUseCase(any(), Unit, any()) }.answers {
            thirdArg<(Resource<List<Note>?>) -> Unit>()(Resource.Success(notes))
        }

        vehicleListViewModel.getVehicles()

        val statefulResponse = vehicleListViewModel.vehicleLiveData.getOrAwaitValue()
        Truth.assertThat(statefulResponse.status).isEqualTo(StatefulResource.Status.SUCCESSES)
        Truth.assertThat(statefulResponse.errorMessage).isNull()
        Truth.assertThat(statefulResponse.status).isNotEqualTo(StatefulResource.Status.LOADING)
        Truth.assertThat(statefulResponse.status).isNotEqualTo(StatefulResource.Status.FAILED)


        Truth.assertThat(statefulResponse.data?.filter {
            it?.id == vehicleId
        }?.get(0)?.notes?.get(0)).isEqualTo(note)


    }

}