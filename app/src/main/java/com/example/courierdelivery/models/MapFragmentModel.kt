package com.example.courierdelivery.models

import com.example.courierdelivery.models.interfaces.MapFragmentModelInterface
import com.example.courierdelivery.models.services.LocationAccessSub
import com.example.courierdelivery.models.services.MapService
import entities.interfaces.SimpleTask
import entities.routeMaps.PlaceMark
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit.DirectionsService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapFragmentModel @Inject constructor(
    private val mapService: MapService,
    private val directionsService: DirectionsService
) : MapFragmentModelInterface {

    override fun getPlaceMark(): PlaceMark? =
        mapService.placeMark

    override fun subscribeOnLocationAccessChanges(subscriber: LocationAccessSub) {
        mapService.subscribe(subscriber)
    }

    override fun unsubscribeOnLocationAccessChanges(subscriber: LocationAccessSub) {
        mapService.unsubscribe(subscriber)
    }

    override fun getDirections(original: String, destination: String, task: SimpleTask) {
        CoroutineScope(IO).launch {
            directionsService.getDirections(original, destination)
        }
    }


}