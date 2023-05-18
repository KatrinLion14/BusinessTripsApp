package com.example.businesstripsapp.request_details_activity.request_details_fragment.outgoing_request_details_fragment.domain

import com.example.businesstripsapp.request_details_activity.models.Request
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

const val REQUEST_URL: String = "/requests/{uuid}"

interface OutgoingRequestDetailsApi {
    @GET(REQUEST_URL)
    fun getRequest(@Path("uuid") id: String): Single<Response<Request>>
}