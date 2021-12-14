package com.example.courierdelivery.models.interfaces

import entities.interfaces.SimpleTask
import entities.routeMaps.Client
import entities.routeMaps.Provider
import entities.routeMaps.RouteItem

interface RouteItemDialogMenuModelInterface {
    fun getClientById(id: Int): Client
    fun getProviderById(id: Int): Provider
    fun changeRouteItemStatusToSelected(routeItem: RouteItem)
    fun changeRouteItemStatusToComoleted(routeItem: RouteItem, simpleTask: SimpleTask)
}