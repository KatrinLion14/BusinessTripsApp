package com.example.businesstripsapp.request_edit_activity.domain

import com.example.businesstripsapp.request_edit_activity.domain.models.DestinationUpdate
import com.example.businesstripsapp.request_edit_activity.domain.models.Request
import com.example.businesstripsapp.request_edit_activity.domain.models.RequestUpdate
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

const val EDIT_REQUEST_URL: String = "/requests"
const val EDIT_DESTINATION_URL: String = "/destinations"
const val REQUEST_URL: String = "/requests/{uuid}"

interface EditRequestApi {
    @GET(REQUEST_URL)
    fun getRequest(@Path("uuid") id: String): Single<Response<Request>>

    @PUT(EDIT_REQUEST_URL)
    fun editRequest(@Body requestUpdate: RequestUpdate): Single<Response<Void>>

    @PUT(EDIT_DESTINATION_URL)
    fun editDestination(@Body destinationUpdate: DestinationUpdate): Single<Response<Void>>
}