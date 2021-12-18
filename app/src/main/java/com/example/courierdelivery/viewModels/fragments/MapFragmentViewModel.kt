package com.example.courierdelivery.viewModels.fragments

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.courierdelivery.models.interfaces.MapFragmentModelInterface
import com.example.courierdelivery.models.services.LocationAccessSub
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import entities.ErrorMessage
import entities.Event
import entities.interfaces.DestinationTask
import entities.routeMaps.PlaceMark

class MapFragmentViewModel(
    private val model: MapFragmentModelInterface,
) : ViewModel() {

    private var _locationAccessEvent: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val locationAccessEvent: LiveData<Event<Boolean>> = _locationAccessEvent

    private var _locationUpdateEvent: MutableLiveData<Event<Location>> = MutableLiveData()
    val locationUpdateEvent: LiveData<Event<Location>> = _locationUpdateEvent

    private var _newDirectionEvent: MutableLiveData<Event<String>> = MutableLiveData()
    val newDirectionEvent: LiveData<Event<String>> = _newDirectionEvent

    private val subscriber: LocationAccessSub = { _locationAccessEvent.value = Event(it) }

    var lastLocation: Location? = null
    val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                _locationUpdateEvent.value = Event(location)
            }
        }
    }

    init {
        model.subscribeOnLocationAccessChanges(subscriber)
    }

    fun getPlaceMark(): PlaceMark? = model.getPlaceMark()

    override fun onCleared() {
        super.onCleared()
        model.unsubscribeOnLocationAccessChanges(subscriber)
    }

    fun getDirection() {
        val original = lastLocation?.latitude.toString() + "," + lastLocation?.longitude
        model.getDirections(original, object: DestinationTask {
            override fun onSuccess(vararg arg: String) {
                _newDirectionEvent.value = Event(arg[0])
            }

            override fun onError(message: ErrorMessage?) {
                TODO("Not yet implemented")
            }
        })
    }
}