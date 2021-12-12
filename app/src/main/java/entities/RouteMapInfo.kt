package entities

import entities.routeMaps.Client
import entities.routeMaps.Provider
import entities.routeMaps.RouteMap

data class RouteMapInfo(
    val routeMap: RouteMap,
    val clients: List<Client>,
    val providers: List<Provider>,
) {
}