package com.example.courierdelivery.models

import com.example.courierdelivery.models.interfaces.MapFragmentModelInterface
import com.example.courierdelivery.models.services.LocationAccessSub
import com.example.courierdelivery.models.services.MapService
import entities.routeMaps.PlaceMark
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapFragmentModel @Inject constructor(
    private val mapService: MapService
) : MapFragmentModelInterface {

    override fun getPlaceMark(): PlaceMark? =
        mapService.placeMark

    override fun subscribeOnLocationAccessChanges(subscriber: LocationAccessSub) {
        mapService.subscribe(subscriber)
    }

    override fun unsubscribeOnLocationAccessChanges(subscriber: LocationAccessSub) {
        mapService.unsubscribe(subscriber)
    }
}