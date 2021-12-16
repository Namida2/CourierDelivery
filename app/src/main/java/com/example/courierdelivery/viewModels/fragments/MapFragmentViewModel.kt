package com.example.courierdelivery.viewModels.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.courierdelivery.models.interfaces.MapFragmentModelInterface
import com.example.courierdelivery.models.services.LocationAccessSub
import entities.Event
import entities.routeMaps.PlaceMark

class MapFragmentViewModel(
    private val model: MapFragmentModelInterface,
) : ViewModel() {

    private var _locationAccessEvent: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val locationAccessEvent: LiveData<Event<Boolean>> = _locationAccessEvent

    private val subscriber: LocationAccessSub = { _locationAccessEvent.value = Event(it) }

    init {
        model.subscribeOnLocationAccessChanges(subscriber)
    }

    fun getPlaceMark(): PlaceMark? = model.getPlaceMark()

    override fun onCleared() {
        super.onCleared()
        model.unsubscribeOnLocationAccessChanges(subscriber)
    }
}