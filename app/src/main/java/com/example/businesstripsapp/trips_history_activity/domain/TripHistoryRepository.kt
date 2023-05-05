package com.example.businesstripsapp.trips_history_activity.domain

import com.example.businesstripsapp.trips_history_activity.models.Trip
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit

class TripHistoryRepository(retrofit: Retrofit) {
    private val tripHistoryApi: TripHistoryApi = retrofit.create(TripHistoryApi::class.java)

    fun getAllTrips(id: String): Observable<Response<Array<Trip>>> {
        return tripHistoryApi.getAllTrips(id).toObservable()
    }
}