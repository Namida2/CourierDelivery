package com.example.courierdelivery.models

import com.example.courierdelivery.models.interfaces.RouteMapMenuDialogModelInterface
import com.example.courierdelivery.models.services.RouteMapsService
import javax.inject.Inject

class RouteMapMenuDialogModel @Inject constructor(
    private val routeMapsService: RouteMapsService,
): RouteMapMenuDialogModelInterface {

    override fun removeRouteMap(routeMapId: Int) {
        routeMapsService.removeRouteMapById(routeMapId)
    }

}