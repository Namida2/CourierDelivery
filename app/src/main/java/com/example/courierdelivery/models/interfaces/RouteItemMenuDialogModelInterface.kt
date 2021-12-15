package com.example.courierdelivery.models.interfaces

import entities.interfaces.SimpleTask
import entities.routeMaps.Client
import entities.routeMaps.Provider
import entities.routeMaps.RouteItem

interface RouteItemMenuDialogModelInterface {
    fun getClientById(id: Int): Client
    fun getProviderById(id: Int): Provider
    fun changeRouteItemStatusToSelected(routeItem: RouteItem)
    fun changeRouteItemStatusToCompleted(routeItem: RouteItem, simpleTask: SimpleTask)
}