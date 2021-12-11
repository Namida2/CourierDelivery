package com.example.courierdelivery.models.interfaces

import com.example.courierdelivery.models.services.ClientsService
import com.example.courierdelivery.models.services.ProviderService
import com.example.courierdelivery.models.services.RouteMapsService

interface RouteMapServicesInterface {
    val clientsService: ClientsService
    val providerService: ProviderService
    val routeMapsService: RouteMapsService

}