package com.example.courierdelivery.viewModels.fragments

import androidx.lifecycle.ViewModel
import com.example.courierdelivery.models.interfaces.MapFragmentModelInterface
import entities.routeMaps.PlaceMark

class MapFragmentViewModel (
    private val model: MapFragmentModelInterface,
) : ViewModel() {

    fun getPlaceMark(): PlaceMark?
    = model.getPlaceMark()
}