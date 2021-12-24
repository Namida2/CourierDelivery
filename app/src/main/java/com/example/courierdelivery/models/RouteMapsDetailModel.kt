package com.example.courierdelivery.models

import com.example.courierdelivery.models.interfaces.RouteMapsDetailModelInterface
import com.example.courierdelivery.models.services.RouteMapItemsSubscriber
import com.example.courierdelivery.models.services.RouteMapsService
import entities.RouteMapInfo
import javax.inject.Inject

class RouteMapsDetailModel @Inject constructor(
    private val routeMapService: RouteMapsService
): RouteMapsDetailModelInterface {

    override fun getRouteMapById(id: Int): RouteMapInfo =
        routeMapService.getRouteMapInfoById(id)

    override fun subscribe(subscriber: RouteMapItemsSubscriber) {
        routeMapService.subscribeOnRouteItemsChanges(subscriber)
    }

    override fun unsubscribe(subscriber: RouteMapItemsSubscriber) {
        routeMapService.unsubscribeOnRouteItemsChanges(subscriber)
    }

}