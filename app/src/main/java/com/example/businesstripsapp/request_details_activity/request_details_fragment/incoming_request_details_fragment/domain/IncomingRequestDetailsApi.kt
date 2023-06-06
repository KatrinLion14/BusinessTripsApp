package com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.domain

import com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.domain.models.Accommodation
import com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.domain.models.CreateAccommodation
import com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.domain.models.CreateTrip
import com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.domain.models.Request
import com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.domain.models.RequestChangeStatus
import com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.domain.models.Trip
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

const val REQUEST_URL: String = "/requests/{uuid}"
const val APPROVE_REQUEST: String = "/requests/{uuid}/approve"
const val DECLINE_REQUEST: String = "/requests/{uuid}/decline"
const val SEND_FOR_EDITING_REQUEST: String = "/requests/{uuid}/send-for-editing"
const val UPDATE_REQUEST: String = "/requests"
const val CREATE_ACCOMMODATION_URL: String = "/accommodations"
const val CREATE_TRIP_URL: String = "/trips"

interface IncomingRequestDetailsApi {
    @GET(REQUEST_URL)
    fun getRequest(@Path("uuid") id: String): Single<Response<Request>>

    @PUT(APPROVE_REQUEST)
    fun approveRequest(@Path("uuid") id: String, @Body requestChangeStatus: RequestChangeStatus): Single<Response<String>>

    @PUT(DECLINE_REQUEST)
    fun declineRequest(@Path("uuid") id: String, @Body requestChangeStatus: RequestChangeStatus): Single<Response<Void>>

    @PUT(SEND_FOR_EDITING_REQUEST)
    fun sendRequestForEditing(@Path("uuid") id: String, @Body requestChangeStatus: RequestChangeStatus): Single<Response<Void>>

    @PUT(UPDATE_REQUEST)
    fun updateRequest(@Path("uuid") id: String): Single<Response<Void>>

    @POST(CREATE_ACCOMMODATION_URL)
    fun createAccommodation(@Body accommodation: Accommodation): Single<Response<CreateAccommodation>>

    @POST(CREATE_TRIP_URL)
    fun createTrip(@Body trip: Trip): Single<Response<CreateTrip>>
}