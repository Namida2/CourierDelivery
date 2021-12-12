package entities.routeMaps

data class RouteItem(
    val addressTypes: AddressTypes,
    val clientId: Int,
    val status: RouteItemStatus = RouteItemStatus.DEFAULT,
) {
}