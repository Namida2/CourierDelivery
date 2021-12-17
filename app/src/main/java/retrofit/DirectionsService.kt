package retrofit

import entities.directions.DirectionsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DirectionsService {
    @GET("/maps/api/directions/json")
    suspend fun getDirections(
        @Query("origin") origin: String,
        @Query("destination") direction: String,
        @Query("mode") mode: String = "walking",
    ): DirectionsResponse
}

