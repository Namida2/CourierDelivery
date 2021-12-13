package com.example.courierdelivery.models.interfaces

import com.example.courierdelivery.models.services.RouteMapInfoSubscriber
import entities.RouteMapInfo

interface RouteMapsDetailModelInterface {
    fun getRouteMapById(id: Int): RouteMapInfo
    fun subscribe(subscriber: RouteMapInfoSubscriber)
    fun unsubscribe(subscriber: RouteMapInfoSubscriber)

}