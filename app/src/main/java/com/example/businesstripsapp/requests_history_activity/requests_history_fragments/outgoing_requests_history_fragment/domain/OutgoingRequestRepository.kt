package com.example.businesstripsapp.requests_history_activity.requests_history_fragments.outgoing_requests_history_fragment.domain

import com.example.businesstripsapp.requests_history_activity.requests_history_fragments.outgoing_requests_history_fragment.domain.models.Request
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit

class OutgoingRequestRepository(retrofit: Retrofit) {
    private val requestApi: OutgoingRequestApi = retrofit.create(OutgoingRequestApi::class.java)

    fun getAllRequests(id: String): Observable<Response<Array<Request>>> {
        return requestApi.getAllRequests(id).toObservable()
    }
}