package com.example.courierdelivery.models.services.interfaces

import com.example.courierdelivery.models.services.RouteMapInfoSubscriber
import entities.RouteMapInfo
import entities.routeMaps.Client
import entities.routeMaps.Provider
import entities.routeMaps.RouteItem
import entities.routeMaps.RouteItemStatus

interface RouteMapsServiceInterface {
    fun getRouteMapInfoById(id: Int): RouteMapInfo
    fun getClientById(id: Int): Client
    fun getProviderById(id: Int): Provider
    fun subscribeOnCurrentRouteMapChanges(subscriber: RouteMapInfoSubscriber)
    fun unsubscribe(subscriber: RouteMapInfoSubscriber)
    fun changeRouteItemStatusToSelected(routeItem: RouteItem)
    fun changeRouteItemStatusToCompleted(routeItem: RouteItem)
}