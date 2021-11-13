package com.mavenusrs.vehicles.data.repository

import com.google.common.truth.Truth
import com.mavenusrs.common.UnitTest
import com.mavenusrs.vehicles.common.Resource
import com.mavenusrs.vehicles.data.entities.NoteEntity
import com.mavenusrs.vehicles.data.remote.APIService
import com.mavenusrs.vehicles.domain.model.Note
import com.mavenusrs.vehicles.domain.usecase.notes.NotesRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class NotesRepositoryImplTest : UnitTest() {
    private lateinit var notesRepository: NotesRepository

    @MockK
    private lateinit var apiService: APIService

    @MockK
    private lateinit var notesResponse: Response<List<NoteEntity?>?>

    @Before
    fun setup() {
        notesRepository = NotesRepositoryImpl(apiService)
    }

    @Test
    fun `check return data Error if getVehiclesNotes return null`() = runBlockingTest {
        every { notesResponse.body() } returns null
        every { notesResponse.isSuccessful } returns true
        coEvery { apiService.getVehiclesNotes() } returns notesResponse

        val notes = notesRepository.getVehiclesNotes()
        notes.collect {
            Truth.assertThat(it).isEqualTo(
                Resource.Failed<List<Note?>?>(
                    null,
                    failureType = Resource.FailureType.DATA_FAILURE
                )
            )
        }
    }

    @Test
    fun `check return server Error if response is Unsuccessful`() = runBlockingTest {
        every { notesResponse.errorBody()?.toString() } returns ""
        every { notesResponse.isSuccessful } returns false
        coEvery { apiService.getVehiclesNotes() } returns notesResponse

        val notes = notesRepository.getVehiclesNotes()
        notes.collect {
            Truth.assertThat(it).isInstanceOf(Resource.Failed::class.java)
            Truth.assertThat((it as Resource.Failed).failureType).isEqualTo(
                Resource.FailureType.SERVER_FAILURE
            )
        }
    }

    @Test
    fun `check getVehiclesNotes return successfully`() = runBlockingTest {
        val vehicleNoteEntity = NoteEntity(
            "White",
            2,
        )
        val notesEntityList = listOf(vehicleNoteEntity)

        every { notesResponse.body() } returns notesEntityList
        every { notesResponse.isSuccessful } returns true
        coEvery { apiService.getVehiclesNotes() } returns notesResponse

        val notes = notesRepository.getVehiclesNotes()
        notes.collect { resource ->
            Truth.assertThat(resource).isEqualTo(
                Resource.Success(
                    notesEntityList.map { note ->
                        note.mapTo()
                    }
                )
            )
        }
    }

}