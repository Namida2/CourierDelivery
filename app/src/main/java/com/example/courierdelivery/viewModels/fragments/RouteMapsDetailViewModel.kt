package com.example.courierdelivery.viewModels.fragments

import androidx.lifecycle.ViewModel
import com.example.courierdelivery.models.interfaces.RouteMapsDetailModelInterface
import entities.RouteMapInfo

class RouteMapsDetailViewModel(
    private val model: RouteMapsDetailModelInterface,
) : ViewModel() {

    fun getRouteMapById(id: Int): RouteMapInfo =
        model.getRouteMapById(id)
}