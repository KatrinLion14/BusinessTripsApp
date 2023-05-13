package com.example.businesstripsapp.trips_activity.domain

import com.example.businesstripsapp.trips_activity.models.Accommodation
import com.example.businesstripsapp.trips_activity.models.Trip
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

const val TRIP_URL: String = "/users/{uuid}/trips-at/1"

interface TripApi {
    @GET(TRIP_URL)
    fun getAllTrips(@Path("uuid") id: String): Single<Response<Array<Trip>>>
}

