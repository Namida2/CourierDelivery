package com.example.courierdelivery.models.services


import entities.routeMaps.Provider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProviderService @Inject constructor(){
    var providers = ArrayList<Provider>()
}