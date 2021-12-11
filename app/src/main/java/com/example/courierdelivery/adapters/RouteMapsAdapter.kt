package com.example.courierdelivery.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.courierdelivery.databinding.LayoutRouteMapBinding
import com.example.courierdelivery.models.interfaces.RouteMapServicesInterface
import com.example.courierdelivery.models.services.RouteMapsService
import javax.inject.Inject

class RouteMapsAdapter @Inject constructor(
    private val routeMapsService: RouteMapsService
) : RecyclerView.Adapter<RouteMapsAdapter.ViewHolder>(), View.OnClickListener {

    private val dot: String = "."

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
            number.text = position.toString() + dot
            addressesCount.text = routeMapsService.routeMaps[position]
                .routeItems.size.toString()
            progress.text = routeMapsService.routeMaps[position].status
        }

    }

    override fun getItemCount(): Int = routeMapsService.getRouteMapsCount()

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}
