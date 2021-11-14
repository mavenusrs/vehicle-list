package com.mavenusrs.vehicles.features.vehicle_list_feat

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mavenusrs.vehicles.R
import com.mavenusrs.vehicles.databinding.VehicleRowBinding
import com.mavenusrs.vehicles.domain.model.Vehicle

class VehicleAdapter : ListAdapter<Vehicle, VehicleAdapter.VehicleViewHolder>(VehiclesDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val binding = VehicleRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return VehicleViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: VehicleViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        Log.d("VehicleAdapter", "onBindViewHolder:${payloads}")

        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {

            val notes: List<String>? = payloads[0] as List<String>?
            if (notes != null)
                holder.bindNotes(notes)
        }
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        Log.d("VehicleAdapter", "onBindViewHolder")

        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id?.toLong() ?: 0L
    }


    class VehicleViewHolder(binding: VehicleRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val vehicleImageView = binding.ivVehicle
        private val vehicleTitleTextView = binding.tvTitle
        private val vehiclePriceTextView = binding.tvPrice
        private val vehicleFuelTextView = binding.tvFuel
        private val vehicleNotesTextView = binding.tvNotes

        fun bind(vehicle: Vehicle) {
            vehicleFuelTextView.text = vehicle.fuel ?: ""
            vehiclePriceTextView.text = "${vehicle.price ?: ""}"

            val title = "${vehicle.make ?: ""} / ${vehicle.model ?: ""}"
            vehicleTitleTextView.text = title

            bindNotes(vehicle.notes)

            val firstImage = vehicle.images?.get(0)?.url ?: ""

            Glide
                .with(itemView.context)
                .setDefaultRequestOptions(RequestOptions())
                .load(firstImage)
                .centerCrop()
                .placeholder(R.drawable.place_holder)
                .into(vehicleImageView)
        }

        fun bindNotes(noteList: List<String>?) {
            val notes = StringBuilder()
            noteList?.map {
                notes.append(it)
            }
            vehicleNotesTextView.text = notes.toString()
        }

    }
}