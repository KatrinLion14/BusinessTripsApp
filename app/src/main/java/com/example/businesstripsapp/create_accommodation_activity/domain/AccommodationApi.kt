package com.example.businesstripsapp.create_accommodation_activity.domain

import com.example.businesstripsapp.create_accommodation_activity.domain.models.Accommodation
import com.example.businesstripsapp.create_accommodation_activity.domain.models.CreateAccommodation
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

const val CREATE_ACCOMODATION_URL: String = "/accommodations"

interface AccommodationApi {
    @POST(CREATE_ACCOMODATION_URL)
    fun createAccommodation(@Body accomodation: Accommodation): Single<Response<CreateAccommodation>>
}