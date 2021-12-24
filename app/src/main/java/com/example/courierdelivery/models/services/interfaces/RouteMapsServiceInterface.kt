package com.example.courierdelivery.models.services.interfaces

import com.example.courierdelivery.models.services.RouteMapItemsSubscriber
import com.example.courierdelivery.models.services.RouteMapsSubscriber
import entities.RouteMapInfo
import entities.routeMaps.Client
import entities.routeMaps.PlaceMark
import entities.routeMaps.Provider
import entities.routeMaps.RouteItem

interface RouteMapsServiceInterface{
    fun getPlaceMark(routeItem: RouteItem): PlaceMark
    fun getRouteMapInfoById(id: Int): RouteMapInfo
    fun getProviderById(id: Int): Provider
    fun getClientById(id: Int): Client

    fun changeRouteItemStatusToSelected(routeItem: RouteItem)
    fun changeRouteItemStatusToCompleted(routeItem: RouteItem)
    fun removeRouteMapById(routeMapId: Int)

    fun subscribeOnRouteItemsChanges(subscriber: RouteMapItemsSubscriber)
    fun unsubscribeOnRouteItemsChanges(subscriber: RouteMapItemsSubscriber)

    fun subscribeOnRouteMapsChanges(subscriber: RouteMapsSubscriber)
    fun unsubscribeOnRouteMapsChanges(subscriber: RouteMapsSubscriber)
}