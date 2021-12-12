package com.example.courierdelivery.models.services

import entities.RouteMapInfo
import javax.inject.Inject
import javax.inject.Singleton

// retrofit
@Singleton
class RouteMapsService @Inject constructor() {
    val message: String = "Route not found. id: "
    var routeMapsInfo: MutableSet<RouteMapInfo> = mutableSetOf()

    fun getRouteMapById(id: Int): RouteMapInfo =
        routeMapsInfo.find {
            it.routeMap.id == id
        } ?: throw IllegalArgumentException(message + id)
}


//class RouteMapsService @Inject constructor() : BaseService<RouteMap> {
//
//    override val message: String = "Route not found. id: "
//    override var items: MutableSet<RouteMap> = mutableSetOf()
//
//    override fun getById(id: Int): RouteMap =
//        items.find {
//            it.id == id
//        } ?: throw IllegalArgumentException(message + id)
//}