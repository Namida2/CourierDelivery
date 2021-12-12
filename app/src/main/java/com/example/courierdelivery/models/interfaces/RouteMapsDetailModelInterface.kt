package com.example.courierdelivery.models.interfaces

import entities.RouteMapInfo

interface RouteMapsDetailModelInterface {
    fun getRouteMapById(id: Int): RouteMapInfo
}