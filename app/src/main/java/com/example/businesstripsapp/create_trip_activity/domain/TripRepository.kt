package com.example.businesstripsapp.create_trip_activity.domain

import com.example.businesstripsapp.create_trip_activity.domain.models.CreateTrip
import com.example.businesstripsapp.create_trip_activity.domain.models.Trip
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit

class TripRepository(retrofit: Retrofit)  {
    private val tripApi: TripApi = retrofit.create(TripApi::class.java)

    fun createTrip(trip: Trip): Observable<Response<CreateTrip>> {
        return tripApi.createTrip(trip)
            .toObservable()
    }
}