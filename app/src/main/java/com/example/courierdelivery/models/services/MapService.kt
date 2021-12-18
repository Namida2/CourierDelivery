package com.example.courierdelivery.models.services

import com.example.courierdelivery.models.services.interfaces.MapServiceInterface
import entities.routeMaps.PlaceMark
import javax.inject.Inject
import javax.inject.Singleton

typealias LocationAccessSub = (locationAccess: Boolean) -> Unit

@Singleton
class MapService @Inject constructor() : MapServiceInterface {
    private val destinationDelimiter = ","
    override var placeMark: PlaceMark? = null
    private val subscribers: MutableSet<LocationAccessSub> = mutableSetOf()
    override var locationAccess: Boolean = false
        set(value) {
            field = value
            notifyChanges()
        }

    fun getDestination(): String =
        placeMark?.latitude.toString() +
                destinationDelimiter +
                placeMark?.longitude

    override fun subscribe(subscriber: LocationAccessSub) {
        subscribers.add(subscriber)
    }

    override fun unsubscribe(subscriber: LocationAccessSub) {
        subscribers.remove(subscriber)
    }

    private fun notifyChanges() {
        subscribers.forEach { it.invoke(locationAccess) }
    }

}