package com.example.courierdelivery.models.services

import com.example.courierdelivery.models.interfaces.RouteMapServicesInterface
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RouteMapsServices @Inject constructor(
    override val clientsService: ClientsService,
    override val providerService: ProviderService,
    override val routeMapsService: RouteMapsService,
): RouteMapServicesInterface