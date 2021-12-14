package com.example.courierdelivery.models

import com.example.courierdelivery.models.interfaces.RouteItemDialogMenuModelInterface
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

class RouteItemDialogMenuModel @Inject constructor(
    //private val retrofitThings
    private val routeMapsService: RouteMapsService,
) : RouteItemDialogMenuModelInterface {

    override fun getClientById(id: Int): Client =
        routeMapsService.getClientById(id)

    override fun getProviderById(id: Int): Provider =
        routeMapsService.getProviderById(id)

    override fun changeRouteItemStatusToSelected(routeItem: RouteItem) {
        routeMapsService.changeRouteItemStatusToSelected(routeItem)
    }

    override fun changeRouteItemStatusToComoleted(
        routeItem: RouteItem,
        task: SimpleTask,
    ) {
        postRouteItemStatus(routeItem, object : SimpleTask {
            override fun onSuccess(vararg arg: Unit) {
                routeMapsService.changeRouteItemStatusToCompleted(routeItem)
                task.onSuccess()
            }

            override fun onError(message: ErrorMessage?) {
                task.onError(message)
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