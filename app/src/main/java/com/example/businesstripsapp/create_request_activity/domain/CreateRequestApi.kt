package com.example.businesstripsapp.create_request_activity.domain

import com.example.businesstripsapp.create_request_activity.domain.models.CreateRequest
import com.example.businesstripsapp.create_request_activity.domain.models.Request
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

const val CREATE_REQUEST_URL: String = "/requests"

interface CreateRequestApi {
    @POST(CREATE_REQUEST_URL)
    fun createRequest(@Body request: Request): Single<Response<CreateRequest>>
}