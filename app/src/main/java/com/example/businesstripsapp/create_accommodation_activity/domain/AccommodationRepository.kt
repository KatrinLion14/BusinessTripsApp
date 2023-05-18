package com.example.businesstripsapp.create_accommodation_activity.domain

import com.example.businesstripsapp.create_accommodation_activity.domain.models.Accommodation
import com.example.businesstripsapp.create_accommodation_activity.domain.models.CreateAccommodation
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit

class AccommodationRepository(retrofit: Retrofit)  {
    private val accommodationApi: AccommodationApi = retrofit.create(AccommodationApi::class.java)

    fun createAccommodation(accommodation: Accommodation): Observable<Response<CreateAccommodation>> {
        return accommodationApi.createAccommodation(accommodation)
            .toObservable()
    }
}