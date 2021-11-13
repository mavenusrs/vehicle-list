package com.mavenusrs.vehicles.data.entities

import com.mavenusrs.vehicles.common.Mapper
import com.mavenusrs.vehicles.domain.model.Note

data class NoteEntity(
    val note: String?,
    val vehicleId: Int?,
): Mapper<Note> {
    override fun mapTo(): Note {
        return Note(
            note,
            vehicleId,
        )
    }

}