package com.example.businesstripsapp.trips_history_activity.domain

import com.example.businesstripsapp.trips_history_activity.models.Trip
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

const val TRIP_HISTORY_URL: String = "/users/{uuid}/trips-at/1"

interface TripHistoryApi {
    @GET(TRIP_HISTORY_URL)
    fun getAllTrips(@Path("uuid") id: String): Single<Response<Array<Trip>>>
}

