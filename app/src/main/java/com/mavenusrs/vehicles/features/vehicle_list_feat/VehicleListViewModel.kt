package com.mavenusrs.vehicles.features.vehicle_list_feat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mavenusrs.vehicles.common.Resource
import com.mavenusrs.vehicles.domain.model.Note
import com.mavenusrs.vehicles.domain.model.Vehicle
import com.mavenusrs.vehicles.domain.usecase.notes.GetVehiclesNotesUseCase
import com.mavenusrs.vehicles.domain.usecase.vehicles.GetVehiclesUseCase
import com.mavenusrs.vehicles.features.common.StatefulResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class VehicleListViewModel @Inject constructor(
    private val getVehiclesUseCase: GetVehiclesUseCase,
    private val getVehiclesNotesUseCase: GetVehiclesNotesUseCase
) :
    ViewModel() {
    private val job = Job()

    private val _vehiclesLiveData = MutableLiveData<StatefulResource<List<Vehicle?>?>>()
    val vehicleLiveData: LiveData<StatefulResource<List<Vehicle?>?>>
        get() = _vehiclesLiveData

    fun getVehicles() {
        _vehiclesLiveData.value =
            StatefulResource(status = StatefulResource.Status.LOADING)
        getVehiclesUseCase(job, Unit) {
            when (it) {
                is Resource.Success -> {
                    handleVehiclesSuccess(it.data)
                }
                is Resource.Failed -> {
                    handleError(it)
                }
            }
        }
    }

    private fun getNotes() {
        getVehiclesNotesUseCase(job, Unit) {
            when (it) {
                is Resource.Success -> {
                    handleNotesSuccess(it.data)
                }
                is Resource.Failed -> {
                    handleNotesError(it)
                }
            }
        }
    }

    private fun handleNotesError(failed: Resource.Failed<List<Note?>?>) {
        Log.d("error:", " ${failed.data?.message ?: failed.failureType}")
    }

    private fun handleNotesSuccess(notes: List<Note?>?) {
        _vehiclesLiveData.value?.data?.map { vehicle ->
            if (vehicle == null)
                return@map

            notes?.filter { notes ->
                if (notes == null)
                    return@filter false
                notes.vehicleId == vehicle.id
            }?.map {
                if (vehicle.notes == null)
                    vehicle.notes = mutableListOf()
                vehicle.notes!!.add(it?.note)
            }
        }
    }

    private fun handleError(error: Resource.Failed<*>) {
        _vehiclesLiveData.value = StatefulResource(
            status = StatefulResource.Status.FAILED,
            errorMessage = error.data?.message ?: "An error occurred", throwable = error.data
        )
    }

    private fun handleVehiclesSuccess(vehicles: List<Vehicle?>?) {
        if (vehicles == null) {
            _vehiclesLiveData.value = StatefulResource(
                status = StatefulResource.Status.IS_EMPTY
            )
        } else {
            _vehiclesLiveData.value = StatefulResource(
                status = StatefulResource.Status.SUCCESSES,
                data = vehicles
            )

            getNotes()
        }

    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}