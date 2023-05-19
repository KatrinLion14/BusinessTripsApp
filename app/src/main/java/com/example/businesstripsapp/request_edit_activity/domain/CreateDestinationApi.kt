package com.example.businesstripsapp.request_edit_activity.domain

import com.example.businesstripsapp.create_request_activity.domain.models.CreateDestination
import com.example.businesstripsapp.create_request_activity.domain.models.Destination
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

const val CREATE_DESTINATION_URL: String = "/destinations"

interface CreateDestinationApi {
    @POST(CREATE_DESTINATION_URL)
    fun createDestination(@Body destination: Destination): Single<Response<CreateDestination>>
}