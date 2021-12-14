package com.example.courierdelivery.viewModels.dialogs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.courierdelivery.models.interfaces.RouteItemDialogMenuModelInterface
import entities.ErrorMessage
import entities.Event
import entities.interfaces.SimpleTask
import entities.routeMaps.Client
import entities.routeMaps.Provider
import entities.routeMaps.RouteItem
import entities.routeMaps.RouteItemStatus
import entities.tools.ErrorMessages.defaultMessage

typealias ShowDetailEvent = Event<Pair<Client, Provider>>

sealed class RouteItemDialogMenuVMStates {
    object Default: RouteItemDialogMenuVMStates()
    object PostingData: RouteItemDialogMenuVMStates()
    object OnPostingSuccessful: RouteItemDialogMenuVMStates()
    class OnPostingFailure(
        val message: ErrorMessage = defaultMessage
    ): RouteItemDialogMenuVMStates()
}

class RouteItemDialogMenuViewModel(
    private val model: RouteItemDialogMenuModelInterface
): ViewModel() {

    var routeItem: RouteItem? = null

    private var _showDetailEvent: MutableLiveData<ShowDetailEvent> = MutableLiveData()
    var showDetailEvent: LiveData<ShowDetailEvent> = _showDetailEvent

    private var _markOnTheMaEvent: MutableLiveData<Event<Unit>> = MutableLiveData()
    var markOnTheMaEvent: LiveData<Event<Unit>> = _markOnTheMaEvent

    private var _state: MutableLiveData<RouteItemDialogMenuVMStates> = MutableLiveData()
    val state: LiveData<RouteItemDialogMenuVMStates> = _state

    fun onShowDetailsButtonClick() {
        val client = model.getClientById(routeItem!!.clientId)
        val provider = model.getProviderById(client.providerId)
        _showDetailEvent.value = Event(Pair(client, provider))
    }

    fun onMarkOnTheMapButtonClick() {
        _markOnTheMaEvent.value = Event(Unit)
        model.changeRouteItemStatusToSelected(routeItem!!)
    }

    fun onMarkAsCompletedButtonClick() {
        _state.value = RouteItemDialogMenuVMStates.PostingData
        model.changeRouteItemStatusToComoleted(routeItem!!, object : SimpleTask {
            override fun onSuccess(vararg arg: Unit) {
                _state.value = RouteItemDialogMenuVMStates.OnPostingSuccessful
            }

            override fun onError(message: ErrorMessage?) {
                _state.value = RouteItemDialogMenuVMStates.OnPostingFailure(
                    message ?: defaultMessage
                )
            }
        })
    }

    fun resetState () {
        _state.value = RouteItemDialogMenuVMStates.Default
    }
}