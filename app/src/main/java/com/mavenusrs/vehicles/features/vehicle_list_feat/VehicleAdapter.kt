package com.mavenusrs.vehicles.features.vehicle_list_feat

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mavenusrs.vehicles.R
import com.mavenusrs.vehicles.databinding.Vehicle2RowBinding
import com.mavenusrs.vehicles.databinding.VehicleRowBinding
import com.mavenusrs.vehicles.domain.model.Image
import com.mavenusrs.vehicles.domain.model.Vehicle

class VehicleAdapter(private val onItemClick: (Vehicle) -> Unit) :
    ListAdapter<Vehicle, VehicleAdapter.VehicleWithVPHolder>(VehiclesDiffUtil()) {

    private val rvPool = RecyclerView.RecycledViewPool()

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
        Log.d("VehicleAdapter", "onBindViewHolder${callNumber++}")

        holder.bind(getItem(position))

        holder.galleryRecyclerView.apply {
            suppressLayout(true)
            setRecycledViewPool(rvPool)
            PagerSnapHelper().attachToRecyclerView(this)

            val linearLayoutManager = LinearLayoutManager(holder.itemView.context)
            linearLayoutManager.recycleChildrenOnDetach = true
            linearLayoutManager.orientation = RecyclerView.HORIZONTAL
            layoutManager = linearLayoutManager

            val gAdapter = GalleryAdapter { onItemClick(getItem(position)) }
            adapter = gAdapter
            // to fix viewpager, if there is not images
            gAdapter.submitList(getItem(position).images ?: listOf(Image("")))

            holder.bindNavigators()
        }

    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id?.toLong() ?: 0L
    }

    class VehicleWithVPHolder(binding: Vehicle2RowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val galleryRecyclerView = binding.rvGallery
        private val nextImageView = binding.ivNext
        private val previousImageView = binding.ivPrevious
        private val vehicleTitleTextView = binding.tvTitle
        private val vehiclePriceTextView = binding.tvPrice
        private val vehicleFuelTextView = binding.tvFuel
        private val vehicleNotesTextView = binding.tvNotes

        init {
            nextImageView.setOnClickListener {
                Log.d("VehicleAdapter", "nextImageViewClick")

                val llm = galleryRecyclerView.layoutManager as LinearLayoutManager
                val currentItemIndex = llm.findFirstCompletelyVisibleItemPosition()
                val scrollTo = minOf(
                    currentItemIndex + 1,
                    (galleryRecyclerView.adapter?.itemCount ?: 0) - 1
                )
                galleryRecyclerView.smoothScrollToPosition(scrollTo)

                bindNavigators(scrollTo)
            }

            previousImageView.setOnClickListener {
                Log.d("VehicleAdapter", "previousImageViewClick")

                val llm = galleryRecyclerView.layoutManager as LinearLayoutManager
                val currentItemIndex =
                    llm.findFirstCompletelyVisibleItemPosition()

                val scrollTo = maxOf(currentItemIndex - 1, 0)
                galleryRecyclerView.smoothScrollToPosition(scrollTo)
                bindNavigators(scrollTo)
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

        fun bindNavigators(currentItemIndex: Int = 0) {
            Log.d(
                "VehicleAdapter",
                "current $currentItemIndex size ${galleryRecyclerView.adapter?.itemCount}"
            )

            val showNext =
                currentItemIndex < (galleryRecyclerView.adapter?.itemCount ?: 0) - 1
            nextImageView.visibility = if (showNext) View.VISIBLE else View.INVISIBLE

            val showPrevious = currentItemIndex > 0
            previousImageView.visibility = if (showPrevious) View.VISIBLE else View.INVISIBLE
        }
    }

    companion object {
        private var callNumber = 0
    }
}