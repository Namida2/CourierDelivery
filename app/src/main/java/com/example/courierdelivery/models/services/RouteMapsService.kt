package com.example.courierdelivery.models.services

import com.example.courierdelivery.models.services.interfaces.RouteMapsServiceInterface
import entities.RouteMapInfo
import entities.routeMaps.*
import entities.tools.ExceptionMessages.CLIENT_NOT_FOUND_MESSAGE
import entities.tools.ExceptionMessages.PROVIDER_NOT_FOUND_MESSAGE
import entities.tools.ExceptionMessages.ROUTE_NOT_FOUND_MESSAGE
import javax.inject.Inject
import javax.inject.Singleton

typealias RouteMapItemsSubscriber = (routeItems: MutableList<RouteItem>) -> Unit
typealias RouteMapsSubscriber = (routeMaps: MutableList<RouteMapInfo>) -> Unit

@Singleton
class RouteMapsService @Inject constructor() : RouteMapsServiceInterface {

    //TODO: Decomposition into two classes

    var currentRouteMapInfo: RouteMapInfo? = null
    var routeMapsInfo: MutableList<RouteMapInfo> = mutableListOf()
    private val routeItemsSubscribers: MutableSet<RouteMapItemsSubscriber> = mutableSetOf()
    private val routeMapsSubscribers: MutableSet<RouteMapsSubscriber> = mutableSetOf()
    private var lastSelectedRouteIem: RouteItem? = null
    private val maxStatus = 100
    private val percentSign = "%"

    override fun subscribeOnRouteItemsChanges(subscriber: RouteMapItemsSubscriber) {
        routeItemsSubscribers.add(subscriber)
    }

    override fun unsubscribeOnRouteItemsChanges(subscriber: RouteMapItemsSubscriber) {
        routeItemsSubscribers.remove(subscriber)
    }

    override fun subscribeOnRouteMapsChanges(subscriber: RouteMapsSubscriber) {
        routeMapsSubscribers.add(subscriber)
    }

    override fun unsubscribeOnRouteMapsChanges(subscriber: RouteMapsSubscriber) {
        routeMapsSubscribers.remove(subscriber)
    }

    private fun notifyRouteMapItemsSubscribers(routeItems: MutableList<RouteItem>) {
        routeItemsSubscribers.forEach {
            it.invoke(routeItems)
        }
    }
    private fun notifyRouteMapsSubscriber(routeItems: MutableList<RouteMapInfo>) {
        routeMapsSubscribers.forEach {
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
        if (routeItems.size != 0) {
            notifyRouteMapItemsSubscribers(routeItems)
            currentRouteMapInfo!!.routeMap.routeItems.clear()
            currentRouteMapInfo!!.routeMap.routeItems.addAll(routeItems)
        }
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
        if (routeItems.size != 0) {
            currentRouteMapInfo!!.routeMap.routeItems.clear()
            currentRouteMapInfo!!.routeMap.routeItems.addAll(routeItems)
            setStatus()
            notifyRouteMapItemsSubscribers(routeItems)
        }
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

    private fun setStatus() {
        val routeItemsCount: Float = currentRouteMapInfo!!.routeMap.routeItems.size.toFloat()
        val oneItemWeight: Float = maxStatus / routeItemsCount
        var currentStatus = 0f
        currentRouteMapInfo!!.routeMap.routeItems.forEach {
            if (it.status == RouteItemStatus.COMPLETED)
                currentStatus += oneItemWeight
        }
        currentRouteMapInfo!!.routeMap.status = currentStatus.toString() + percentSign
    }


    override fun removeRouteMapById(routeMapId: Int) {
        routeMapsInfo.remove(
            getRouteMapInfoById(routeMapId)
        )
        if(currentRouteMapInfo?.routeMap?.id == routeMapId)
            currentRouteMapInfo = null
        notifyRouteMapsSubscriber(routeMapsInfo)
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
