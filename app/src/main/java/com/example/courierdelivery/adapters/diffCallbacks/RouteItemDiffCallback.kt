package com.example.courierdelivery.adapters.diffCallbacks

import androidx.recyclerview.widget.DiffUtil
import entities.routeMaps.RouteItem

class RouteItemDiffCallback: DiffUtil.ItemCallback<RouteItem>() {
    override fun areItemsTheSame(oldItem: RouteItem, newItem: RouteItem): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: RouteItem, newItem: RouteItem): Boolean = oldItem == newItem
}