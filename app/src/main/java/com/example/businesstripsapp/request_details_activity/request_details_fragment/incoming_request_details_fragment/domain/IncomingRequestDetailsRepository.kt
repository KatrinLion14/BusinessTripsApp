package com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.domain

import com.example.businesstripsapp.request_details_activity.models.Request
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit

class IncomingRequestDetailsRepository(retrofit: Retrofit) {
    private val requestApi: IncomingRequestDetailsApi = retrofit.create(IncomingRequestDetailsApi::class.java)
    fun getRequest(id: String): Observable<Response<Request>> {
        return requestApi.getRequest(id).toObservable()
    }

    fun approveRequest(id: String): Observable<Response<String>> {
        return requestApi.approveRequest(id).toObservable()
    }

    fun declineRequest(id: String): Observable<Response<String>> {
        return requestApi.declineRequest(id).toObservable()
    }

    fun sendRequestForEditing(id: String): Observable<Response<String>> {
        return requestApi.sendRequestForEditing(id).toObservable()
    }

    fun updateRequest(id: String): Observable<Response<String>> {
        return requestApi.updateRequest(id).toObservable()
    }


}