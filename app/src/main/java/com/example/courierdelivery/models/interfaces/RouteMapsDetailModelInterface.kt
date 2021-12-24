package com.example.courierdelivery.models.interfaces

import com.example.courierdelivery.models.services.RouteMapItemsSubscriber
import entities.RouteMapInfo

interface RouteMapsDetailModelInterface {
    fun getRouteMapById(id: Int): RouteMapInfo
    fun subscribe(subscriber: RouteMapItemsSubscriber)
    fun unsubscribe(subscriber: RouteMapItemsSubscriber)

}