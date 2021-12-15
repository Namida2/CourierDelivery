package com.example.courierdelivery.models

import com.example.courierdelivery.models.interfaces.RouteItemMenuDialogModelInterface
import com.example.courierdelivery.models.services.MapService
import com.example.courierdelivery.models.services.RouteMapsService
import entities.ErrorMessage
import entities.interfaces.SimpleTask
import entities.routeMaps.Client
import entities.routeMaps.Provider
import entities.routeMaps.RouteItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RouteItemMenuDialogModel @Inject constructor(
    private val mapService: MapService,
    private val routeMapsService: RouteMapsService,
) : RouteItemMenuDialogModelInterface {

    override fun getClientById(id: Int): Client =
        routeMapsService.getClientById(id)

    override fun getProviderById(id: Int): Provider =
        routeMapsService.getProviderById(id)

    override fun changeRouteItemStatusToSelected(routeItem: RouteItem) {
        mapService.placeMark = routeMapsService.getPlaceMark(routeItem)
        routeMapsService.changeRouteItemStatusToSelected(routeItem)
    }

    override fun changeRouteItemStatusToCompleted(
        routeItem: RouteItem,
        simpleTask: SimpleTask,
    ) {
        postRouteItemStatus(routeItem, object : SimpleTask {
            override fun onSuccess(vararg arg: Unit) {
                routeMapsService.changeRouteItemStatusToCompleted(routeItem)
                simpleTask.onSuccess()
            }

            override fun onError(message: ErrorMessage?) {
                simpleTask.onError(message)
            }
        })
    }

    private fun postRouteItemStatus(
        routeItem: RouteItem,
        simpleTask: SimpleTask,
    ) {
        CoroutineScope(IO).launch {
            delay(1600)
            withContext(Main) {
                simpleTask.onSuccess()
            }
        }
    }

}