package com.example.courierdelivery.viewModels.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.courierdelivery.models.interfaces.RouteMapsModelInterface
import entities.ErrorMessage
import entities.ErrorMessages.defaultMessage
import entities.ErrorMessages.emptyRouteMapsListMessage

sealed class RouteMapsVMStates {
    object Default : RouteMapsVMStates()
    object ReadingData : RouteMapsVMStates()
    object ReadingWasSuccessful : RouteMapsVMStates()
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
            if (it) {
                _state.value = RouteMapsVMStates.DataIsEmpty()
                return@getRouteMaps
            }
            _state.value = RouteMapsVMStates.ReadingWasSuccessful
        }, onError = {
            _state.value = RouteMapsVMStates.ReadingWasFailure(it ?: defaultMessage)
        })
    }
}