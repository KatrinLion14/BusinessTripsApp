package com.example.businesstripsapp.trips_activity.domain

import com.example.businesstripsapp.trips_activity.models.Trip
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit

class TripRepository(retrofit: Retrofit) {
    private val tripApi: TripApi = retrofit.create(TripApi::class.java)

    fun getAllTrips(id: String): Observable<Response<Array<Trip>>> {
        return tripApi.getAllTrips(id).toObservable()
    }
}