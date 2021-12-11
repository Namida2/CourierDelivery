package com.example.courierdelivery.models.interfaces

import entities.ErrorMessage

interface RouteMapsModelInterface {
    fun getRouteMaps(
        onSuccess: (isEmpty: Boolean) -> Unit,
        onError: (message: ErrorMessage?) -> Unit
    )
}