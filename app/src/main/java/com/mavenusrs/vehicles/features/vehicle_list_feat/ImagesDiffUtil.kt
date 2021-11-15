package com.mavenusrs.vehicles.features.vehicle_list_feat

import androidx.recyclerview.widget.DiffUtil
import com.mavenusrs.vehicles.domain.model.Image

class ImagesDiffUtil : DiffUtil.ItemCallback<Image>() {
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem.url == newItem.url
    }

    override fun getChangePayload(oldItem: Image, newItem: Image): Any? {
        return newItem.url
    }
}