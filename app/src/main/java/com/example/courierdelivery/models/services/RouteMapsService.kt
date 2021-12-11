package com.example.courierdelivery.models.services

import entities.routeMaps.RouteMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RouteMapsService @Inject constructor(
    // retrofit
) {
    var routeMaps = ArrayList<RouteMap>()
    fun getRouteMapsCount(): Int = routeMaps.size
}