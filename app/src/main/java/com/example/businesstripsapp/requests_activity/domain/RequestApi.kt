package com.example.businesstripsapp.requests_activity.domain

import com.example.businesstripsapp.requests_activity.models.Request
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

const val REQUEST_URL: String = "/users/{uuid}/outgoing-requests-at/"

interface RequestApi {
    @GET(REQUEST_URL)
    fun getAllRequests(@Path("uuid") id: String): Single<Response<Array<Request>>>
}