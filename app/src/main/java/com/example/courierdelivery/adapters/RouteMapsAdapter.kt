package com.example.courierdelivery.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.courierdelivery.databinding.LayoutRouteMapBinding
import entities.RouteMapInfo
import entities.routeMaps.RouteItemStatus
import entities.routeMaps.RouteMap

class RouteMapsAdapter (
    private var routeMapsInfo: List<RouteMapInfo> = listOf()
) : RecyclerView.Adapter<RouteMapsAdapter.ViewHolder>(), View.OnClickListener {

    var onRouteMapClick: ((routeMapId: Int) -> Unit)? = null

    class ViewHolder(
        val binding: LayoutRouteMapBinding,
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutRouteMapBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            number.text = position.toString()
            addressesCount.text = routeMapsInfo[position].routeMap
                .routeItems.size.toString()
            progress.text = routeMapsInfo[position].routeMap.status
            root.tag = routeMapsInfo[position].routeMap.routeId
            setTextColor(holder, routeMapsInfo[position].isSelected)
        }
    }

    private fun setTextColor(holder: ViewHolder, isSelected: Boolean) {
        var color = RouteItemStatus.DEFAULT.color
        if(isSelected) color = RouteItemStatus.SELECTED.color
        with(holder.binding) {
            number.setTextColor(color)
            addressesCountText.setTextColor(color)
            addressesCount.setTextColor(color)
            progressText.setTextColor(color)
            progress.setTextColor(color)
            dot.setTextColor(color)
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setRouteMaps(routeMapsInfo: List<RouteMapInfo>) {
        this.routeMapsInfo = routeMapsInfo
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int = routeMapsInfo.size

    override fun onClick(v: View?) {
        onRouteMapClick?.invoke(v?.tag as Int)
    }
}
