package com.example.businesstripsapp.request_details_activity.request_details_fragment.outgoing_request_details_fragment.domain

import com.example.businesstripsapp.request_details_activity.request_details_fragment.outgoing_request_details_fragment.domain.models.Request
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit

class OutgoingRequestDetailsRepository(retrofit: Retrofit) {
    private val requestApi: OutgoingRequestDetailsApi = retrofit.create(OutgoingRequestDetailsApi::class.java)
    fun getRequest(id: String): Observable<Response<Request>> {
        return requestApi.getRequest(id).toObservable()
    }
}