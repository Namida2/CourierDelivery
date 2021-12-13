package entities.routeMaps

data class Provider(
    val id: Int,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val phone: String,
    val commentary: String,
) {
}