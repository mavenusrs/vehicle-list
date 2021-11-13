package com.mavenusrs.vehicles.di

import com.mavenusrs.vehicles.data.repository.NotesRepositoryImpl
import com.mavenusrs.vehicles.data.repository.VehicleRepositoryImpl
import com.mavenusrs.vehicles.domain.usecase.notes.NotesRepository
import com.mavenusrs.vehicles.domain.usecase.vehicles.VehicleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryBinding {

    @Binds
    fun bindVehicleRepository(vehicleRepositoryImpl: VehicleRepositoryImpl): VehicleRepository

    @Binds
    fun bindNotesRepository(notesRepositoryImpl: NotesRepositoryImpl): NotesRepository
}