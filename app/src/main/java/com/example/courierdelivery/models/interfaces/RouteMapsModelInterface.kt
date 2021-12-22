package com.example.courierdelivery.models.interfaces

import entities.ErrorMessage
import entities.RouteMapInfo

interface RouteMapsModelInterface {
    fun readRouteMaps(
        onSuccess: (routeMaps: List<RouteMapInfo>) -> Unit,
        onError: (message: ErrorMessage?) -> Unit
    )
    fun getRouteMapInfoById(routeMapId: Int): RouteMapInfo
    fun getRouteMapsInfo(): List<RouteMapInfo>
    fun setCurrentRouteMapInfo(routeMapId: Int)
    fun getCurrentRouteMapId(): Int?
}