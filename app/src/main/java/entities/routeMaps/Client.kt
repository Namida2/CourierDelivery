package entities.routeMaps

data class Client (
    val id: Int,
    val providerId: Int,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val phone: String,
    val commentary: String,
    val status: String,
) {

}