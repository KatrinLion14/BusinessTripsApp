package com.example.businesstripsapp.requests_activity.domain

import com.example.businesstripsapp.requests_activity.models.Request
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit

class RequestRepository(retrofit: Retrofit) {
    private val requestApi: RequestApi = retrofit.create(RequestApi::class.java)

    fun getAllRequests(id: String): Observable<Response<Array<Request>>> {
        return requestApi.getAllRequests(id).toObservable()
    }
}