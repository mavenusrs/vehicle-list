package com.mavenusrs.vehicles.domain.usecase.notes

import com.mavenusrs.vehicles.common.BaseUseCase
import com.mavenusrs.vehicles.common.Resource
import com.mavenusrs.vehicles.domain.model.Note
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class GetVehiclesNotesUseCase @Inject constructor(private val notesRepository: NotesRepository) :
    BaseUseCase<Unit, List<Note>?>() {

    override suspend fun run(param: Unit): Flow<Resource<List<Note>?>> {
        return notesRepository.getVehiclesNotes()
    }

}