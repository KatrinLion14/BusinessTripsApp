package com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.domain

import com.example.businesstripsapp.request_details_activity.models.Request
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

const val REQUEST_URL: String = "/requests/{uuid}"
const val APPROVE_REQUEST: String = "/requests/{uuid}/approve"
const val DECLINE_REQUEST: String = "/requests/{uuid}/decline"
const val SEND_FOR_EDITING_REQUEST: String = "/requests/{uuid}/send-for-editing"
const val UPDATE_REQUEST: String = "/requests"

interface IncomingRequestDetailsApi {
    @GET(REQUEST_URL)
    fun getRequest(@Path("uuid") id: String): Single<Response<Request>>

    @PUT(APPROVE_REQUEST)
    fun approveRequest(@Path("uuid") id: String)

    @PUT(DECLINE_REQUEST)
    fun declineRequest(@Path("uuid") id: String)

    @PUT(SEND_FOR_EDITING_REQUEST)
    fun SendRequestForEditing(@Path("uuid") id: String)

    @PUT(UPDATE_REQUEST)
    fun UpdateRequest(@Path("uuid") id: String)
}