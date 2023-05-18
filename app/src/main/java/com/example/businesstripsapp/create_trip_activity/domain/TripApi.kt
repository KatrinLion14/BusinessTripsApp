package com.example.businesstripsapp.create_trip_activity.domain

import com.example.businesstripsapp.create_trip_activity.domain.models.CreateTrip
import com.example.businesstripsapp.create_trip_activity.domain.models.Trip
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

const val CREATE_TRIP_URL: String = "/trips"

interface TripApi {
    @POST(CREATE_TRIP_URL)
    fun createTrip(@Body trip: Trip): Single<Response<CreateTrip>>
}