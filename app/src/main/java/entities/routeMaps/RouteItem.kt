package entities.routeMaps

data class RouteItem(
    val addressTypes: AddressTypes,
    val clientId: Int,
    var status: RouteItemStatus = RouteItemStatus.DEFAULT,
) {
}