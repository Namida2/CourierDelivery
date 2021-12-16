package com.example.courierdelivery.models.services.interfaces

import com.example.courierdelivery.models.services.RouteMapInfoSubscriber
import entities.RouteMapInfo
import entities.interfaces.BaseObservable
import entities.routeMaps.Client
import entities.routeMaps.PlaceMark
import entities.routeMaps.Provider
import entities.routeMaps.RouteItem

interface RouteMapsServiceInterface: BaseObservable<RouteMapInfoSubscriber>{
    fun getRouteMapInfoById(id: Int): RouteMapInfo
    fun getClientById(id: Int): Client
    fun getProviderById(id: Int): Provider

    fun changeRouteItemStatusToSelected(routeItem: RouteItem)
    fun changeRouteItemStatusToCompleted(routeItem: RouteItem)
    fun getPlaceMark(routeItem: RouteItem): PlaceMark
}