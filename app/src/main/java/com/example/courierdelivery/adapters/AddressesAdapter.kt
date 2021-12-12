package com.example.courierdelivery.adapters

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.courierdelivery.databinding.LayoutAddressItemBinding
import entities.RouteMapInfo
import entities.routeMaps.AddressTypes
import entities.routeMaps.Client
import entities.routeMaps.CourierActions
import entities.routeMaps.RouteItemStatus
import java.util.*

class AddressesAdapter(
    private var routeMapInfo: RouteMapInfo,
    private var defaultColorStateList: ColorStateList,
    private var completedColorStateList: ColorStateList,
    private var selectedColorStateList: ColorStateList,

) : RecyclerView.Adapter<AddressesAdapter.ViewHolder>(), View.OnClickListener {

    private val message = "Unknown address type. type: "
    private val number = "№П"
    private val orderIdDelimiter = "_"

    class ViewHolder(
        val binding: LayoutAddressItemBinding,
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutAddressItemBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val routeItem = routeMapInfo.routeMap.routeItems[position]
        val client = routeMapInfo.clients.find { it.id == routeItem.clientId }!!
        when (routeItem.addressTypes) {
            AddressTypes.CLIENT -> {
                itemIsClient(holder.binding, client)
            }
            AddressTypes.PROVIDER -> {
                itemIsProvider(holder.binding, client)
            }
        }
        setColors(routeItem.status, holder.binding)
    }

    private fun setColors(status: RouteItemStatus, binding: LayoutAddressItemBinding) {
        with(binding) {
            orderId.setTextColor(status.color)
            textGetTo.setTextColor(status.color)
            address.setTextColor(status.color)
            action.setTextColor(status.color)
            when(status) {
                RouteItemStatus.DEFAULT -> point.backgroundTintList = defaultColorStateList
                RouteItemStatus.COMPLETED -> point.backgroundTintList = completedColorStateList
                RouteItemStatus.SELECTED -> point.backgroundTintList = selectedColorStateList
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun itemIsClient(binding: LayoutAddressItemBinding, client: Client) {
        with(binding) {
            orderId.text = number + client.providerId + orderIdDelimiter + client.id
            address.text = client.latitude.toString() + " | " + client.longitude
            action.text = CourierActions.CLIENT_ACTION.action
        }
    }

    @SuppressLint("SetTextI18n")
    private fun itemIsProvider(binding: LayoutAddressItemBinding, client: Client) {
        val provider = routeMapInfo.providers.find { it.id == client.providerId }!!
        with(binding) {
            orderId.text = number + client.providerId + orderIdDelimiter + client.id
            address.text = provider.latitude.toString() + " | " + provider.longitude
            action.text = CourierActions.PROVIDER_ACTION.action
        }
    }

    override fun getItemCount(): Int = routeMapInfo.routeMap.routeItems.size

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }


}