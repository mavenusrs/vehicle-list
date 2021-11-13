package com.mavenusrs.vehicles.domain.usecase

import com.mavenusrs.common.UnitTest
import com.mavenusrs.vehicles.common.Resource
import com.mavenusrs.vehicles.domain.model.Note
import com.mavenusrs.vehicles.domain.usecase.notes.GetVehiclesNotesUseCase
import com.mavenusrs.vehicles.domain.usecase.notes.NotesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetNotesUseCaseTest : UnitTest() {

    private lateinit var getVehiclesNotesUseCase: GetVehiclesNotesUseCase

    @MockK
    private lateinit var notesRepository: NotesRepository

    @Before
    fun setup() {
        getVehiclesNotesUseCase = GetVehiclesNotesUseCase(notesRepository)
    }

    @Test
    fun `should call get notes from Repository only once`() = runBlockingTest {
        coEvery { notesRepository.getVehiclesNotes() } returns flow {
            emit(Resource.Success(emptyList<Note>()))
        }

        getVehiclesNotesUseCase.run(Unit)
        coVerify(exactly = 1) {
            notesRepository.getVehiclesNotes()
        }
    }

}