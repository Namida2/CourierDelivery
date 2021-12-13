package com.example.courierdelivery.models

import com.example.courierdelivery.models.interfaces.RouteItemDialogMenuModelInterface
import com.example.courierdelivery.models.services.RouteMapsService
import entities.routeMaps.Client
import entities.routeMaps.Provider
import entities.routeMaps.RouteItem
import entities.routeMaps.RouteItemStatus
import javax.inject.Inject

class RouteItemDialogMenuModel @Inject constructor(
    private val routeMapsService: RouteMapsService,
) : RouteItemDialogMenuModelInterface {

    override fun getClientById(id: Int): Client =
        routeMapsService.getClientById(id)
    override fun getProviderById(id: Int): Provider =
        routeMapsService.getProviderById(id)

    override fun changeRouteIemStatus(routeItem: RouteItem, status: RouteItemStatus) {
        routeMapsService.changeRouteIemStatus(routeItem, status)
    }

}