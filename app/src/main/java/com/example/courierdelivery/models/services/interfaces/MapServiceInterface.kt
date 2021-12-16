package com.example.courierdelivery.models.services.interfaces

import com.example.courierdelivery.models.services.LocationAccessSub
import entities.interfaces.BaseObservable
import entities.routeMaps.PlaceMark

interface MapServiceInterface: BaseObservable<LocationAccessSub>{
    var placeMark: PlaceMark?
    var locationAccess: Boolean
}