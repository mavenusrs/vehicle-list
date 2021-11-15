package com.mavenusrs.vehicles.features.vehicle_list_feat

import androidx.recyclerview.widget.DiffUtil
import com.mavenusrs.vehicles.domain.model.Vehicle

class VehiclesDiffUtil : DiffUtil.ItemCallback<Vehicle>() {

    override fun getChangePayload(oldItem: Vehicle, newItem: Vehicle): Any? {
        if (newItem.notes?.size != oldItem.notes?.size)
            return newItem.notes
        return null
    }

    override fun areItemsTheSame(oldItem: Vehicle, newItem: Vehicle): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Vehicle, newItem: Vehicle): Boolean {
        return oldItem == newItem
    }
}