package entities.routeMaps

data class RouteMap(
    val id: Int,
    val courierNumber: String,
    val routeId: Int,
    val routeItems: List<RouteItem>,
    val status: String,
    val current_longitude: Double,
    val current_latitude: Double,
    ) {
}