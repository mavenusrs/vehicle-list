package com.mavenusrs.vehicles.features.vehicle_list_feat

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mavenusrs.vehicles.R
import com.mavenusrs.vehicles.databinding.VehicleGallaryRowBinding
import com.mavenusrs.vehicles.domain.model.Image

class GalleryAdapter(private val onItemClick: () -> Unit) :
    ListAdapter<Image, GalleryAdapter.ImageViewHolder>(ImagesDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = VehicleGallaryRowBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ImageViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val imagePath = payloads[0] as String
            holder.bind(imagePath)
        }
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Log.d("GalleryAdapter", "${callNumber++}")
        holder.itemView.setOnClickListener {
            onItemClick.invoke()
        }

        holder.bind(getItem(position).url)
    }

    class ImageViewHolder(binding: VehicleGallaryRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val vehicleImageView = binding.ivVehicle

        fun bind(path: String?) {
            Glide
                .with(itemView.context)
                .setDefaultRequestOptions(RequestOptions())
                .load(path)
                .centerCrop()
                .placeholder(R.drawable.place_holder)
                .into(vehicleImageView)
        }

    }

    companion object {
        private var callNumber = 0
    }
}