package com.mavenusrs.vehicles.data.remote

import com.mavenusrs.vehicles.data.entities.NoteEntity
import com.mavenusrs.vehicles.data.entities.VehicleEntity
import retrofit2.Response
import retrofit2.http.GET

interface APIService {

    @GET("http://private-fe87c-simpleclassifieds.apiary-mock.com/")
    suspend fun getVehicles(): Response<List<VehicleEntity?>?>

    @GET("https://private-e7c3d8-classifiednotes.apiary-mock.com/")
    suspend fun getVehiclesNotes(): Response<List<NoteEntity?>?>
}