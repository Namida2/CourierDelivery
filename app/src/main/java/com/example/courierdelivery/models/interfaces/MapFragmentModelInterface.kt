package com.example.courierdelivery.models.interfaces

import com.example.courierdelivery.models.services.LocationAccessSub
import entities.routeMaps.PlaceMark

interface MapFragmentModelInterface {
    fun getPlaceMark(): PlaceMark?
    fun subscribeOnLocationAccessChanges(subscriber: LocationAccessSub)
    fun unsubscribeOnLocationAccessChanges(subscriber: LocationAccessSub)
}