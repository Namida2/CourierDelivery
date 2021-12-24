package com.example.courierdelivery.viewModels.dialogs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.courierdelivery.models.interfaces.RouteMapMenuDialogModelInterface
import entities.Event

class RouteMapMenuDialogViewModel(
    private val model: RouteMapMenuDialogModelInterface
): ViewModel() {

    var routeMapId: Int? = null

    private var _onOpenRouteMapEvent: MutableLiveData<Event<Int>> = MutableLiveData()
    val onOpenRouteMapEvent: LiveData<Event<Int>> = _onOpenRouteMapEvent

    private var _onRemoveRouteMapEvent: MutableLiveData<Event<Int>> = MutableLiveData()
    val onRemoveRouteMapEvent: LiveData<Event<Int>> = _onRemoveRouteMapEvent

    fun onOpenRouteMapButtonClick() {
        _onOpenRouteMapEvent.value = Event(routeMapId!!)
    }

    fun onRemoveRouteMapButtonClick() {
        _onRemoveRouteMapEvent.value = Event(routeMapId!!)
    }

    fun removeRouteMap(routeMapId: Int) {
        model.removeRouteMap(routeMapId)
    }
}