package com.example.courierdelivery.models.services

import com.example.courierdelivery.models.services.interfaces.RouteMapsServiceInterface
import entities.RouteMapInfo
import entities.routeMaps.*
import entities.tools.ExceptionMessages.CLIENT_NOT_FOUND_MESSAGE
import entities.tools.ExceptionMessages.PROVIDER_NOT_FOUND_MESSAGE
import entities.tools.ExceptionMessages.ROUTE_NOT_FOUND_MESSAGE
import javax.inject.Inject
import javax.inject.Singleton

typealias RouteMapInfoSubscriber = (routeItems: MutableList<RouteItem>) -> Unit

@Singleton
class RouteMapsService @Inject constructor() : RouteMapsServiceInterface {

    var currentRouteMapInfo: RouteMapInfo? = null
    var routeMapsInfo: MutableSet<RouteMapInfo> = mutableSetOf()
    private val subscribers: MutableSet<RouteMapInfoSubscriber> = mutableSetOf()
    private var lastSelectedRouteIem: RouteItem? = null

    override fun subscribe(subscriber: RouteMapInfoSubscriber) {
        subscribers.add(subscriber)
    }
    override fun unsubscribe(subscriber: RouteMapInfoSubscriber) {
        subscribers.remove(subscriber)
    }
    private fun notifyChanges(routeItems: MutableList<RouteItem>) {
        subscribers.forEach {
            it.invoke(routeItems)
        }
    }

    override fun changeRouteItemStatusToSelected(routeItem: RouteItem) {
        val routeItems = mutableListOf<RouteItem>()
        currentRouteMapInfo!!.routeMap.routeItems.forEachIndexed { index, item ->
            if (item == routeItem) {
                val newRouteItem = item.copy(status = RouteItemStatus.SELECTED)
                hasSelectedItem(index, newRouteItem, routeItems)
                return@forEachIndexed
            }
        }
        if (routeItems.size != 0) notifyChanges(routeItems)
    }

    override fun changeRouteItemStatusToCompleted(routeItem: RouteItem) {
        val routeItems = mutableListOf<RouteItem>()
        currentRouteMapInfo!!.routeMap.routeItems.forEachIndexed { index, item ->
            if (item == routeItem) {
                val newRouteItem = item.copy(status = RouteItemStatus.COMPLETED)
                hasCompletedItem(index, newRouteItem, routeItems)
                return@forEachIndexed
            }
        }
        if (routeItems.size != 0) notifyChanges(routeItems)
    }

    override fun getPlaceMark(routeItem: RouteItem): PlaceMark {
        val client = getClientById(routeItem.clientId)
        val provider = getProviderById(client.providerId)
        when (routeItem.addressTypes) {
            AddressTypes.CLIENT -> {
                return PlaceMark(
                    client.latitude,
                    client.longitude,
                    provider.name,
                )
            }
            AddressTypes.PROVIDER -> {
                return PlaceMark(
                    provider.latitude,
                    provider.longitude,
                    provider.name,
                )
            }
        }
    }

    private fun hasSelectedItem(
        position: Int,
        newRouteItem: RouteItem,
        routeItems: MutableList<RouteItem>,
    ) {
        currentRouteMapInfo!!.routeMap.routeItems.forEachIndexed { index, item ->
            when {
                item == lastSelectedRouteIem -> {
                    val itemWithDefaultStatus = item.copy(status = RouteItemStatus.DEFAULT)
                    routeItems.add(itemWithDefaultStatus)
                }
                index == position -> routeItems.add(newRouteItem)
                else -> routeItems.add(item)
            }
        }
        lastSelectedRouteIem = newRouteItem
    }

    private fun hasCompletedItem(
        position: Int,
        newRouteItem: RouteItem,
        routeItems: MutableList<RouteItem>,
    ) {
        currentRouteMapInfo!!.routeMap.routeItems.forEachIndexed { index, item ->
            when (index) {
                position -> routeItems.add(newRouteItem)
                else -> routeItems.add(item)
            }
        }
    }


    override fun getRouteMapInfoById(id: Int): RouteMapInfo =
        routeMapsInfo.find {
            it.routeMap.id == id
        } ?: throw IllegalArgumentException(ROUTE_NOT_FOUND_MESSAGE + id)

    override fun getClientById(id: Int): Client {
        var client: Client? = null
        currentRouteMapInfo!!.clients.find {
            if (it.id == id) {
                client = it; true
            } else false
        }
        return client ?: throw IllegalArgumentException(CLIENT_NOT_FOUND_MESSAGE + id)
    }

    override fun getProviderById(id: Int): Provider {
        var provider: Provider? = null
        currentRouteMapInfo!!.providers.find {
            if (it.id == id) {
                provider = it; true
            } else false
        }
        return provider ?: throw IllegalArgumentException(PROVIDER_NOT_FOUND_MESSAGE + id)
    }

}
