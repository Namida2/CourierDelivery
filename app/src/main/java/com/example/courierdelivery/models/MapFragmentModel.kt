package com.example.courierdelivery.models

import com.example.courierdelivery.models.interfaces.MapFragmentModelInterface
import com.example.courierdelivery.models.services.LocationAccessSub
import com.example.courierdelivery.models.services.MapService
import entities.interfaces.DestinationTask
import entities.interfaces.SimpleTask
import entities.routeMaps.PlaceMark
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit.DirectionsService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapFragmentModel @Inject constructor(
    private val mapService: MapService,
    private val directionsService: DirectionsService,
) : MapFragmentModelInterface {

    override fun getPlaceMark(): PlaceMark? =
        mapService.placeMark

    override fun subscribeOnLocationAccessChanges(subscriber: LocationAccessSub) {
        mapService.subscribe(subscriber)
    }

    override fun unsubscribeOnLocationAccessChanges(subscriber: LocationAccessSub) {
        mapService.unsubscribe(subscriber)
    }

    override fun getDirections(original: String, task: DestinationTask) {
        CoroutineScope(IO).launch {
            val response = directionsService
                .getDirections(original, mapService.getDestination())
            //TODO: Handle errors here
            withContext(Main) {
                task.onSuccess(response.routes?.get(0)?.overview_polyline?.points.toString())
            }
        }
    }


}