package com.example.courierdelivery.viewModels.dialogs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.courierdelivery.models.interfaces.RouteItemDialogMenuModelInterface
import entities.Event
import entities.routeMaps.Client
import entities.routeMaps.Provider
import entities.routeMaps.RouteItem
import entities.routeMaps.RouteItemStatus

typealias ShowDetailEvent = Event<Pair<Client, Provider>>

class RouteItemDialogMenuViewModel(
    private val model: RouteItemDialogMenuModelInterface
): ViewModel() {

    var routeItem: RouteItem? = null

    private var _showDetailEvent: MutableLiveData<ShowDetailEvent> = MutableLiveData()
    var showDetailEvent: LiveData<ShowDetailEvent> = _showDetailEvent

    fun onShowDetailsButtonClick() {
        val client = model.getClientById(routeItem!!.clientId)
        val provider = model.getProviderById(client.providerId)
        _showDetailEvent.value = Event(Pair(client, provider))
    }

    fun onMarkOnTheMapButtonClick() {
        model.changeRouteIemStatus(routeItem!!, RouteItemStatus.SELECTED )
    }

    fun onMarkAsCompletedButtonClick() {

    }
}