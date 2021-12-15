package com.example.courierdelivery.models

import com.example.courierdelivery.models.interfaces.MapFragmentModelInterface
import com.example.courierdelivery.models.services.MapService
import entities.routeMaps.PlaceMark
import javax.inject.Inject

class MapFragmentModel @Inject constructor(
    private val mapService: MapService
) : MapFragmentModelInterface {

    override fun getPlaceMark(): PlaceMark? =
        mapService.placeMark
}