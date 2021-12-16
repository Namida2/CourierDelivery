package com.example.courierdelivery.models

import android.transition.TransitionManager
import com.example.courierdelivery.models.interfaces.MainActivityModelInterface
import com.example.courierdelivery.models.services.MapService
import javax.inject.Inject

class MainActivityModel @Inject constructor(
    private val mapService: MapService
) : MainActivityModelInterface {
    override fun setLocationAccess(access: Boolean) {
        mapService.locationAccess = access
    }
}