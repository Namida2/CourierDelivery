package com.example.courierdelivery.models

import com.example.courierdelivery.models.interfaces.RouteMapsDetailModelInterface
import com.example.courierdelivery.models.services.RouteMapInfoSubscriber
import com.example.courierdelivery.models.services.RouteMapsService
import entities.RouteMapInfo
import javax.inject.Inject

class RouteMapsDetailModel @Inject constructor(
    private val routeMapService: RouteMapsService
): RouteMapsDetailModelInterface {

    override fun getRouteMapById(id: Int): RouteMapInfo =
        routeMapService.getRouteMapInfoById(id)

    override fun subscribe(subscriber: RouteMapInfoSubscriber) {
        routeMapService.subscribe(subscriber)
    }

    override fun unsubscribe(subscriber: RouteMapInfoSubscriber) {
        routeMapService.unsubscribe(subscriber)
    }

}