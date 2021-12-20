package com.example.courierdelivery.viewModels.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.courierdelivery.models.interfaces.RouteMapsModelInterface
import entities.ErrorMessage
import entities.Event
import entities.tools.ErrorMessages.defaultMessage
import entities.tools.ErrorMessages.emptyRouteMapsListMessage
import entities.RouteMapInfo

sealed class RouteMapsVMStates {
    object Default : RouteMapsVMStates()
    object ReadingData : RouteMapsVMStates()
    class ReadingWasSuccessful(
        val routeMaps: List<RouteMapInfo>,
    ) : RouteMapsVMStates()

    class ReadingWasFailure(
        val message: ErrorMessage = defaultMessage,
    ) : RouteMapsVMStates()

    class DataIsEmpty(
        val message: ErrorMessage = emptyRouteMapsListMessage,
    ) : RouteMapsVMStates()
}

class RouteMapsFragmentViewModel(
    private val model: RouteMapsModelInterface,
) : ViewModel() {

    private var _state: MutableLiveData<RouteMapsVMStates> =
        MutableLiveData(RouteMapsVMStates.Default)
    val state: LiveData<RouteMapsVMStates> = _state

    private var _onCurrentRouteMapEmpty: MutableLiveData<Event<Int>> = MutableLiveData()
    val onCurrentRouteMapEmpty: LiveData<Event<Int>> = _onCurrentRouteMapEmpty

    private var _onRouteMapClickEvent: MutableLiveData<Event<Int>> = MutableLiveData()
    val onRouteMapClickEvent: LiveData<Event<Int>> = _onRouteMapClickEvent

    //TODO: Add method for getting routeMaps

    fun onRefreshButtonClick() {
        if (state.value is RouteMapsVMStates.ReadingData) return
        _state.value = RouteMapsVMStates.ReadingData
        model.getRouteMaps(onSuccess = {
            if (it.isEmpty()) {
                _state.value = RouteMapsVMStates.DataIsEmpty()
                return@getRouteMaps
            }
            _state.value = RouteMapsVMStates.ReadingWasSuccessful(it)
        }, onError = {
            _state.value = RouteMapsVMStates.ReadingWasFailure(it ?: defaultMessage)
        })
    }

    fun onRouteMapClick(routeMapId: Int) {
        val currentRouteMapId = model.getCurrentRouteMapId()
        when (currentRouteMapId) {
            null -> _onCurrentRouteMapEmpty.value = Event(routeMapId)
            routeMapId -> _onRouteMapClickEvent.value = Event(routeMapId)
            else -> {
                //TODO: Show the message
            }
        }
    }

    fun setCurrentRouteMapInfo(routeMapId: Int) {
        model.setCurrentRouteMapInfo(routeMapId)
    }

}