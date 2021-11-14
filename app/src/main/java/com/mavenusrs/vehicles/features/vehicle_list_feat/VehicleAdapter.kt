package com.mavenusrs.vehicles.features.vehicle_list_feat

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.mavenusrs.vehicles.R
import com.mavenusrs.vehicles.databinding.VehicleRowBinding
import com.mavenusrs.vehicles.domain.model.Vehicle

class VehicleAdapter : RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder>() {

    private val vehicles = mutableListOf<Vehicle>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val binding = VehicleRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return VehicleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        holder.bind(vehicles[position])
    }

    override fun getItemCount(): Int {
        return vehicles.size
    }

    override fun getItemId(position: Int): Long {
        return vehicles[position].id?.toLong() ?: 0L
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(list: List<Vehicle>) {
        vehicles.clear()
        vehicles.addAll(list)
        notifyDataSetChanged()
    }


    class VehicleViewHolder(binding: VehicleRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val vehicleImageView = binding.ivVehicle
        private val vehicleTitleTextView = binding.tvTitle
        private val vehiclePriceTextView = binding.tvPrice
        private val vehicleFuelTextView = binding.tvFuel

        fun bind(vehicle: Vehicle) {
            vehicleFuelTextView.text = vehicle.fuel ?: ""
            vehiclePriceTextView.text = "${vehicle.price ?: ""}"

            val title = "${vehicle.make ?: ""} / ${vehicle.model ?: ""}"
            vehicleTitleTextView.text = title

            val firstImage = vehicle.images?.get(0)?.url ?: ""

            Glide
                .with(itemView.context)
                .setDefaultRequestOptions(RequestOptions())
                .load(firstImage)
                .centerCrop()
                .placeholder(R.drawable.place_holder)
                .into(vehicleImageView)
        }

    }
}