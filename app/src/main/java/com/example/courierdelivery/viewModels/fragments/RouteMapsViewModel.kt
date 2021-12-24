package com.example.courierdelivery.viewModels.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.courierdelivery.models.interfaces.RouteMapsModelInterface
import com.example.courierdelivery.models.services.RouteMapsSubscriber
import constants.RouteMapConstants.COMPLETED_ROUTE_MAP
import entities.ErrorMessage
import entities.Event
import entities.RouteMapInfo
import entities.routeMaps.RouteMap
import entities.tools.ErrorMessages.defaultMessage
import entities.tools.ErrorMessages.emptyRouteMapsListMessage
import entities.tools.ErrorMessages.routeMapAlreadyUsed

typealias RouteMapRemovedEvent = Event<MutableList<RouteMapInfo>>

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

    private var _onCompletedRouteMapClickEvent: MutableLiveData<Event<Int>> = MutableLiveData()
    val onCompletedRouteMapClickEvent: LiveData<Event<Int>> = _onCompletedRouteMapClickEvent

    private var _onRouteMapRemovedEvent: MutableLiveData<RouteMapRemovedEvent> = MutableLiveData()
    val onRouteMapRemovedEvent: LiveData<RouteMapRemovedEvent> = _onRouteMapRemovedEvent

    private val subscriber = object : RouteMapsSubscriber {
        override fun invoke(routeMaps: MutableList<RouteMapInfo>) {
            _onRouteMapRemovedEvent.value = Event(routeMaps)
        }
    }

    init {
        model.subscribeOnRouteMapsChanges(subscriber)
    }

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
        if (isCompletedRouteItem(routeMapInfo.routeMap)) return
        when (model.getCurrentRouteMapId()) {
            null -> _onCurrentRouteMapEmpty.value = Event(routeMapId)
            routeMapId -> _onRouteMapClickEvent.value = Event(routeMapId)
            else -> _state.value = RouteMapsVMStates.RouteMapsAlreadyUsed()
        }
    }

    private fun isCompletedRouteItem(routeMap: RouteMap): Boolean =
        if (routeMap.status == COMPLETED_ROUTE_MAP) {
            _onCompletedRouteMapClickEvent.value = Event(routeMap.id)
            true
        } else false


    fun setCurrentRouteMapInfo(routeMapId: Int) {
        model.setCurrentRouteMapInfo(routeMapId)
    }

    fun resetState() {
        _state.value = RouteMapsVMStates.Default
    }

    override fun onCleared() {
        super.onCleared()
        model.unsubscribeOnRouteMapsChanges(subscriber)
    }
}