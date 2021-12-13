package com.example.courierdelivery.viewModels.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.courierdelivery.models.interfaces.RouteMapsModelInterface
import entities.ErrorMessage
import entities.ErrorMessages.defaultMessage
import entities.ErrorMessages.emptyRouteMapsListMessage
import entities.RouteMapInfo
import entities.routeMaps.RouteMap

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

    fun setCurrentRouteMapInfo(routeMapId: Int) {
        model.setCurrentRouteMapInfo(routeMapId)
    }
}