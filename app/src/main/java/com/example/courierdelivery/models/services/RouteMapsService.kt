package com.example.courierdelivery.models.services

import entities.RouteMapInfo
import entities.routeMaps.Client
import entities.routeMaps.Provider
import entities.routeMaps.RouteItem
import entities.routeMaps.RouteItemStatus
import javax.inject.Inject
import javax.inject.Singleton

typealias RouteMapInfoSubscriber = (routeItems: MutableList<RouteItem>) -> Unit

// retrofit
@Singleton
class RouteMapsService @Inject constructor() {
    private val routeExceptionMessage: String = "Route not found. id: "
    private val clientExceptionMessage: String = "Client not found. id: "
    private val providerExceptionMessage: String = "Client not found. id: "
    var routeMapsInfo: MutableSet<RouteMapInfo> = mutableSetOf()

    private val subscribers: MutableSet<RouteMapInfoSubscriber> = mutableSetOf()
    private var lastSelected: Int? = null
    var currentRouteMapInfo: RouteMapInfo? = null

    fun getRouteMapInfoById(id: Int): RouteMapInfo =
        routeMapsInfo.find {
            it.routeMap.id == id
        } ?: throw IllegalArgumentException(routeExceptionMessage + id)

    fun getClientById(id: Int): Client {
        var client: Client? = null
        currentRouteMapInfo!!.clients.find {
            if (it.id == id) { client = it; true }
            else false
        }
        return client ?: throw IllegalArgumentException(clientExceptionMessage + id)
    }

    fun getProviderById(id: Int): Provider {
        var provider: Provider? = null
        currentRouteMapInfo!!.providers.find {
            if (it.id == id) { provider = it; true }
            else false
        }
        return provider ?: throw IllegalArgumentException(providerExceptionMessage + id)
    }

    fun subscribeOnCurrentRouteMapChanges(subscriber: RouteMapInfoSubscriber) {
        subscribers.add(subscriber)
    }

    fun unsubscribe(subscriber: RouteMapInfoSubscriber) {
        subscribers.remove(subscriber)
    }

    private fun notifyChanges(routeItems: MutableList<RouteItem>) {
        subscribers.forEach {
            it.invoke(routeItems)
        }
    }

    //TODO: Prohibit marking multiply routeItems
    //TODO: Prohibit changing the route map while working
    //TODO: Add logic to other buttons
    //TODO: Add showing a mark on the map
    //TODO: Start working with retrofit

    fun changeRouteIemStatus(routeItem: RouteItem, status: RouteItemStatus) {
        val routeItems = mutableListOf<RouteItem>()
        currentRouteMapInfo!!.routeMap.routeItems.forEachIndexed { index, item ->
            if (item == routeItem) {
                val newRouteItem = item.copy(status = status)
                lastSelected = index
                currentRouteMapInfo!!.routeMap.routeItems.forEachIndexed { i, routeItem ->
                    if(i == index) routeItems.add(newRouteItem)
                    else routeItems.add(routeItem)
                }
                return@forEachIndexed
            }
        }
        if(routeItems.size != 0)
            notifyChanges(routeItems)
    }

}
