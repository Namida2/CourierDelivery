package com.example.courierdelivery.models.interfaces

import entities.ErrorMessage
import entities.RouteMapInfo

interface RouteMapsModelInterface {
    fun getRouteMaps(
        onSuccess: (routeMaps: List<RouteMapInfo>) -> Unit,
        onError: (message: ErrorMessage?) -> Unit
    )
    fun setCurrentRouteMapInfo(routeMapId: Int)
}