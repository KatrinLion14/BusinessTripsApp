package com.example.businesstripsapp.trip_info_activity.domain

import com.example.businesstripsapp.trip_info_activity.models.Trip
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

const val TRIP_URL: String = "/trips/{uuid}"

interface TripInfoApi {
    @GET(TRIP_URL)
    fun getTrip(@Path("uuid") id: String): Single<Response<Trip>>
}