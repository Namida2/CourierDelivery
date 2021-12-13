package com.example.courierdelivery.models.interfaces

import entities.routeMaps.Client
import entities.routeMaps.Provider
import entities.routeMaps.RouteItem
import entities.routeMaps.RouteItemStatus

interface RouteItemDialogMenuModelInterface {
    fun getClientById(id: Int): Client
    fun getProviderById(id: Int): Provider
    fun changeRouteIemStatus(routeItem: RouteItem, status: RouteItemStatus)
}