package com.mavenusrs.vehicles.features.vehicle_list_feat

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class VehicleAdapter :
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
        Log.d("VehicleAdapter", "onBindViewHolder${callNumber ++}")

        holder.bind(getItem(position))

        holder.vehicleRecyclerView.apply {

            setRecycledViewPool(rvPool)
            PagerSnapHelper().attachToRecyclerView(this)

            val linearLayoutManager = LinearLayoutManager(holder.itemView.context)
            linearLayoutManager.recycleChildrenOnDetach = true
            linearLayoutManager.orientation = RecyclerView.HORIZONTAL
            layoutManager = linearLayoutManager

            val gAdapter = GalleryAdapter()
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
        val vehicleRecyclerView = binding.vpVehicle
        private val nextImageView = binding.ivNext
        private val previousImageView = binding.ivPrevious
        private val vehicleTitleTextView = binding.tvTitle
        private val vehiclePriceTextView = binding.tvPrice
        private val vehicleFuelTextView = binding.tvFuel
        private val vehicleNotesTextView = binding.tvNotes

        init {
            nextImageView.setOnClickListener {
                Log.d("VehicleAdapter", "nextImageViewClick")

                val llm = vehicleRecyclerView.layoutManager as LinearLayoutManager
                val currentItemIndex = llm.findFirstCompletelyVisibleItemPosition()
                val scrollTo = minOf(
                    currentItemIndex + 1,
                    (vehicleRecyclerView.adapter?.itemCount ?: 0) - 1
                )
                vehicleRecyclerView.smoothScrollToPosition(scrollTo)

                bindNavigators(scrollTo)
            }

            previousImageView.setOnClickListener {
                Log.d("VehicleAdapter", "previousImageViewClick")

                val llm = vehicleRecyclerView.layoutManager as LinearLayoutManager
                val currentItemIndex =
                    llm.findFirstCompletelyVisibleItemPosition()

                val scrollTo = maxOf(currentItemIndex - 1, 0)
                vehicleRecyclerView.smoothScrollToPosition(scrollTo)
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
                "current $currentItemIndex size ${vehicleRecyclerView.adapter?.itemCount}"
            )

            val showNext =
                currentItemIndex < (vehicleRecyclerView.adapter?.itemCount ?: 0) - 1
            nextImageView.visibility = if (showNext) View.VISIBLE else View.INVISIBLE

            val showPrevious = currentItemIndex > 0
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

    companion object {
        private var callNumber = 0
    }
}