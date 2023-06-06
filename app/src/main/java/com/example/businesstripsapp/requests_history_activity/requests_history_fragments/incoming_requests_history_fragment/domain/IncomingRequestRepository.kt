package com.example.businesstripsapp.requests_history_activity.requests_history_fragments.incoming_requests_history_fragment.domain

import com.example.businesstripsapp.requests_history_activity.requests_history_fragments.incoming_requests_history_fragment.domain.models.Request
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit

class IncomingRequestRepository(retrofit: Retrofit) {
    private val requestApi: IncomingRequestApi = retrofit.create(IncomingRequestApi::class.java)

    fun getAllRequests(id: String): Observable<Response<Array<Request>>> {
        return requestApi.getAllRequests(id).toObservable()
    }
}