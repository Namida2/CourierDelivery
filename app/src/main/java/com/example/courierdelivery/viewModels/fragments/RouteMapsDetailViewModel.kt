package com.example.courierdelivery.viewModels.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.courierdelivery.models.interfaces.RouteMapsDetailModelInterface
import com.example.courierdelivery.models.services.RouteMapItemsSubscriber
import entities.Event
import entities.RouteMapInfo
import entities.routeMaps.RouteItem

typealias RouteMapInfoChangedEvent = Event<MutableList<RouteItem>>

class RouteMapsDetailViewModel(
    private val model: RouteMapsDetailModelInterface,
) : ViewModel() {

    private var _routeMapInfoChangedEvent: MutableLiveData<RouteMapInfoChangedEvent> =
        MutableLiveData()
    val routeMapInfoChangedEvent: MutableLiveData<RouteMapInfoChangedEvent> =
        _routeMapInfoChangedEvent

    private val subscriber = object : RouteMapItemsSubscriber {
        override fun invoke(routeItems: MutableList<RouteItem>) {
            _routeMapInfoChangedEvent.value = Event(routeItems)
        }
    }

    init {
        model.subscribe(subscriber)
    }

    fun getRouteMapById(id: Int): RouteMapInfo =
        model.getRouteMapById(id)

    override fun onCleared() {
        super.onCleared()
        model.unsubscribe(subscriber)
    }
}