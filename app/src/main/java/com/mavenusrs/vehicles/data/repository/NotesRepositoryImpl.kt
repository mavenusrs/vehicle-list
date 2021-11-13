package com.mavenusrs.vehicles.data.repository

import com.mavenusrs.vehicles.common.Resource
import com.mavenusrs.vehicles.data.remote.APIService
import com.mavenusrs.vehicles.domain.model.Note
import com.mavenusrs.vehicles.domain.usecase.notes.NotesRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class NotesRepositoryImpl @Inject constructor(private val apiService: APIService) :
    NotesRepository {

    override suspend fun getVehiclesNotes(): Flow<Resource<List<Note?>?>> = flow {
        val response = apiService.getVehiclesNotes()
        emit(when (response.isSuccessful) {
            true -> {
                response.body()?.let {
                    Resource.Success(it.map { note ->
                        note?.mapTo()
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