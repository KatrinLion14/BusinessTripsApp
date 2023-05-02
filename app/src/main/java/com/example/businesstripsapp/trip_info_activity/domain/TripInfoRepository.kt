package com.example.businesstripsapp.trip_info_activity.domain

import com.example.businesstripsapp.trip_info_activity.models.Trip
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit

class TripInfoRepository (retrofit: Retrofit) {
    private val tripInfoApi: TripInfoApi = retrofit.create(TripInfoApi::class.java)

    fun getTrip(id: String): Observable<Response<Trip>> {
        return tripInfoApi.getTrip(id).toObservable()
    }
}