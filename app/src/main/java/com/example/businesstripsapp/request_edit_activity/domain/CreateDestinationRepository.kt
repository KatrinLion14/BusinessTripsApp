package com.example.businesstripsapp.request_edit_activity.domain

import com.example.businesstripsapp.create_request_activity.domain.models.CreateDestination
import com.example.businesstripsapp.create_request_activity.domain.models.Destination
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit

class CreateDestinationRepository(retrofit: Retrofit)  {
    private val destinationApi: CreateDestinationApi = retrofit.create(CreateDestinationApi::class.java)

    fun createDestination(destination: Destination): Observable<Response<CreateDestination>> {
        return destinationApi.createDestination(destination)
            .toObservable()
    }
}