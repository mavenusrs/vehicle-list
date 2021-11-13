package com.mavenusrs.vehicles.domain.usecase.notes

import com.mavenusrs.vehicles.common.Resource
import com.mavenusrs.vehicles.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    suspend fun getVehiclesNotes(): Flow<Resource<List<Note?>?>>
}
