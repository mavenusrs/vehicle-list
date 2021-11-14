package com.mavenusrs.vehicles.features.vehicle_list_feat

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mavenusrs.vehicles.R
import com.mavenusrs.vehicles.databinding.Vehicle2RowBinding
import com.mavenusrs.vehicles.databinding.VehicleRowBinding
import com.mavenusrs.vehicles.domain.model.Image
import com.mavenusrs.vehicles.domain.model.Vehicle
import java.lang.Integer.min

class VehicleAdapter :
    ListAdapter<Vehicle, VehicleAdapter.VehicleWithVPHolder>(VehiclesDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleWithVPHolder {
        val binding = Vehicle2RowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return VehicleWithVPHolder(binding)
    }

    override fun onBindViewHolder(
        holder: VehicleWithVPHolder,
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

    override fun onBindViewHolder(holder: VehicleWithVPHolder, position: Int) {
        Log.d("VehicleAdapter", "onBindViewHolder")

        holder.bind(getItem(position))

        holder.bindViewPager(getItem(position).images)

    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id?.toLong() ?: 0L
    }

    class VehicleWithVPHolder(binding: Vehicle2RowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val vehicleViewPager = binding.vpVehicle
        private val nextImageView = binding.ivNext
        private val previousImageView = binding.ivPrevious
        private val vehicleTitleTextView = binding.tvTitle
        private val vehiclePriceTextView = binding.tvPrice
        private val vehicleFuelTextView = binding.tvFuel
        private val vehicleNotesTextView = binding.tvNotes

        init {
            nextImageView.setOnClickListener {
                Log.d("VehicleAdapter", "nextImageViewClick")
                vehicleViewPager.setCurrentItem(
                    minOf(
                        vehicleViewPager.currentItem + 1,
                        (vehicleViewPager.adapter?.itemCount ?: 0) - 1
                    ),
                    true
                )
                bindNavigators()
            }

            previousImageView.setOnClickListener {
                Log.d("VehicleAdapter", "previousImageViewClick")
                vehicleViewPager.setCurrentItem(
                    maxOf(vehicleViewPager.currentItem - 1, 0),
                    true
                )
                bindNavigators()
            }
        }

        fun bind(vehicle: Vehicle) {
            vehicleFuelTextView.text = vehicle.fuel ?: ""
            vehiclePriceTextView.text = "${vehicle.price ?: ""}"

            val title = "${vehicle.make ?: ""} / ${vehicle.model ?: ""}"
            vehicleTitleTextView.text = title

            bindNotes(vehicle.notes)
        }

        fun bindNotes(noteList: List<String>?) {
            val notes = StringBuilder()
            noteList?.map {
                notes.append("$it , ")
            }
            vehicleNotesTextView.text = notes.toString()
        }

        fun bindViewPager(images: List<Image>?) {
            val adapter = GalleryAdapter()
            // to fix viewpager, if there is not images
            adapter.submitList(images ?: listOf(Image("")))
            vehicleViewPager.adapter = adapter

            bindNavigators()
        }

        private fun bindNavigators() {
            Log.d(
                "VehicleAdapter",
                "current ${vehicleViewPager.currentItem} size ${vehicleViewPager.adapter?.itemCount}"
            )
            val showNext =
                vehicleViewPager.currentItem < (vehicleViewPager.adapter?.itemCount ?: 0) - 1
            nextImageView.visibility = if (showNext) View.VISIBLE else View.INVISIBLE

            val showPrevious = vehicleViewPager.currentItem > 0
            previousImageView.visibility = if (showPrevious) View.VISIBLE else View.INVISIBLE
        }
    }

    @Deprecated("Task 1")
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