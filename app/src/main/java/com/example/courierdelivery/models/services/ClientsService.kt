package com.example.courierdelivery.models.services

import entities.routeMaps.Client
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClientsService @Inject constructor(
    // retrofit
){
    var clients = ArrayList<Client>()
}