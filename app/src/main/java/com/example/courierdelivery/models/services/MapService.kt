package com.example.courierdelivery.models.services

import com.example.courierdelivery.models.services.interfaces.MapServiceInterface
import entities.routeMaps.PlaceMark
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapService @Inject constructor(): MapServiceInterface {
    override var placeMark: PlaceMark? = null
}