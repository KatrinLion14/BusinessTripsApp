package com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.domain

import com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.domain.models.Accommodation
import com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.domain.models.CreateAccommodation
import com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.domain.models.CreateTrip
import com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.domain.models.Request
import com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.domain.models.RequestChangeStatus
import com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.domain.models.Trip
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit

class IncomingRequestDetailsRepository(retrofit: Retrofit) {
    private val requestApi: IncomingRequestDetailsApi = retrofit.create(IncomingRequestDetailsApi::class.java)
    fun getRequest(id: String): Observable<Response<Request>> {
        return requestApi.getRequest(id).toObservable()
    }

    fun approveRequest(id: String, request: RequestChangeStatus): Observable<Response<String>> {
        return requestApi.approveRequest(id, request).toObservable()
    }

    fun declineRequest(id: String, request: RequestChangeStatus): Observable<Response<Void>> {
        return requestApi.declineRequest(id, request).toObservable()
    }

    fun sendRequestForEditing(id: String, request: RequestChangeStatus): Observable<Response<Void>> {
        return requestApi.sendRequestForEditing(id, request).toObservable()
    }

    fun updateRequest(id: String): Observable<Response<Void>> {
        return requestApi.updateRequest(id).toObservable()
    }

    fun createAccommodation(accommodation: Accommodation): Observable<Response<CreateAccommodation>> {
        return requestApi.createAccommodation(accommodation)
            .toObservable()
    }

    fun createTrip(trip: Trip): Observable<Response<CreateTrip>> {
        return requestApi.createTrip(trip)
            .toObservable()
    }

}