package entities.routeMaps

enum class AddressTypes(val type: String) {
    PROVIDER("Поставщик"),
    CLIENT("Клиент"),
}

enum class CourierActions(val action: String) {
    PROVIDER_ACTION("Забрать заказ у поставщика"),
    CLIENT_ACTION("Передать заказ клиенту")
}

enum class RouteItemStatus(val color: Int) {
    DEFAULT(-16711423),
    COMPLETED(-5592406),
    SELECTED(-14579713)
}

enum class OrderStatuses(val status: String) {
    IN_PROGRESS("Исполняется"),
    COMPLETED("Выполнена")
}