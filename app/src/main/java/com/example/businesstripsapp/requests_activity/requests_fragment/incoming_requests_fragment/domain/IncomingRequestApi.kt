package com.example.businesstripsapp.requests_activity.requests_fragment.incoming_requests_fragment.domain

import com.example.businesstripsapp.requests_activity.requests_fragment.incoming_requests_fragment.models.Request
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

const val REQUEST_URL: String = "/users/{uuid}/incoming-requests-at/"

interface IncomingRequestApi {
    @GET(REQUEST_URL)
    fun getAllRequests(@Path("uuid") id: String): Single<Response<Array<Request>>>
}