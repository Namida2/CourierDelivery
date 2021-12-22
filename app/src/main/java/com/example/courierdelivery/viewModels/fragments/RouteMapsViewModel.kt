package com.example.courierdelivery.viewModels.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.courierdelivery.models.interfaces.RouteMapsModelInterface
import entities.ErrorMessage
import entities.Event
import entities.RouteMapInfo
import entities.tools.ErrorMessages.defaultMessage
import entities.tools.ErrorMessages.emptyRouteMapsListMessage
import entities.tools.ErrorMessages.routeMapAlreadyUsed

sealed class RouteMapsVMStates {
    open val message: ErrorMessage? = null

    object Default : RouteMapsVMStates()
    object ReadingData : RouteMapsVMStates()
    class ReadingWasSuccessful(
        val routeMaps: List<RouteMapInfo>,
    ) : RouteMapsVMStates()

    class ReadingWasFailure(
        override val message: ErrorMessage = defaultMessage,
    ) : RouteMapsVMStates()

    class DataIsEmpty(
        override val message: ErrorMessage = emptyRouteMapsListMessage,
    ) : RouteMapsVMStates()

    class RouteMapsAlreadyUsed(
        override val message: ErrorMessage = routeMapAlreadyUsed,
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


    fun getRouteMapsInfo(): List<RouteMapInfo> =
        model.getRouteMapsInfo()

    fun onRefreshButtonClick() {
        if (state.value is RouteMapsVMStates.ReadingData) return
        _state.value = RouteMapsVMStates.ReadingData
        model.readRouteMaps(onSuccess = {
            if (it.isEmpty()) {
                _state.value = RouteMapsVMStates.DataIsEmpty()
                return@readRouteMaps
            }
            _state.value = RouteMapsVMStates.ReadingWasSuccessful(it)
        }, onError = {
            _state.value = RouteMapsVMStates.ReadingWasFailure(it ?: defaultMessage)
        })
    }

    fun onRouteMapClick(routeMapId: Int) {
        val routeMapInfo = model.getRouteMapInfoById(routeMapId)
        //if(routeMapInfo.routeMap.status)
        when (model.getCurrentRouteMapId()) {
            null -> _onCurrentRouteMapEmpty.value = Event(routeMapId)
            routeMapId -> _onRouteMapClickEvent.value = Event(routeMapId)
            else -> _state.value = RouteMapsVMStates.RouteMapsAlreadyUsed()
        }
    }

    fun setCurrentRouteMapInfo(routeMapId: Int) {
        model.setCurrentRouteMapInfo(routeMapId)
    }

    fun resetState() {
        _state.value = RouteMapsVMStates.Default
    }

}