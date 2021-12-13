package com.example.courierdelivery.adapters

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.courierdelivery.adapters.diffCallbacks.RouteItemDiffCallback
import com.example.courierdelivery.databinding.LayoutRouteItemBinding
import entities.RouteMapInfo
import entities.routeMaps.*

//TODO: Add addressBottomSheetDialogMenu
//TODO:

class RouteItemsAdapter(
    private val routeMapInfo: RouteMapInfo,
    private val defaultColorStateList: ColorStateList,
    private val completedColorStateList: ColorStateList,
    private val selectedColorStateList: ColorStateList,
    private val onAddressClick: (routeItem: RouteItem) -> Unit,
) : ListAdapter<RouteItem, RouteItemsAdapter.ViewHolder>(RouteItemDiffCallback()), View.OnClickListener {

    private val number = "№П"
    private val orderIdDelimiter = "_"

    class ViewHolder(
        val binding: LayoutRouteItemBinding,
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutRouteItemBinding.inflate(inflater, parent, false)
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
        holder.binding.root.tag = routeItem
    }

    private fun setColors(status: RouteItemStatus, binding: LayoutRouteItemBinding) {
        with(binding) {
            orderId.setTextColor(status.color)
            textGetTo.setTextColor(status.color)
            address.setTextColor(status.color)
            action.setTextColor(status.color)
            when (status) {
                RouteItemStatus.DEFAULT -> point.backgroundTintList = defaultColorStateList
                RouteItemStatus.COMPLETED -> point.backgroundTintList = completedColorStateList
                RouteItemStatus.SELECTED -> point.backgroundTintList = selectedColorStateList
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun itemIsClient(binding: LayoutRouteItemBinding, client: Client) {
        with(binding) {
            orderId.text = number + client.providerId + orderIdDelimiter + client.id
            address.text = client.address
            action.text = CourierActions.CLIENT_ACTION.action
        }
    }

    @SuppressLint("SetTextI18n")
    private fun itemIsProvider(binding: LayoutRouteItemBinding, client: Client) {
        val provider = routeMapInfo.providers.find { it.id == client.providerId }!!
        with(binding) {
            orderId.text = number + client.providerId + orderIdDelimiter + client.id
            address.text = provider.address
            action.text = CourierActions.PROVIDER_ACTION.action
        }
    }

    override fun submitList(list: MutableList<RouteItem>?) {
        val newList = mutableListOf<RouteItem>()
        list!!.forEach { newList.add(it.copy()) }
        routeMapInfo.routeMap.routeItems.clear()
        routeMapInfo.routeMap.routeItems.addAll(newList)
        super.submitList(newList)
    }

    override fun onClick(v: View?) {
        onAddressClick.invoke(v?.tag as RouteItem)
    }

}