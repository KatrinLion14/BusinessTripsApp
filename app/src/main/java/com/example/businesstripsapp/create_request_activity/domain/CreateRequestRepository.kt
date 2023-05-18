package com.example.businesstripsapp.create_request_activity.domain


import com.example.businesstripsapp.create_request_activity.domain.models.CreateRequest
import com.example.businesstripsapp.create_request_activity.domain.models.Request
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit

class CreateRequestRepository(retrofit: Retrofit)  {
    private val requestApi: CreateRequestApi = retrofit.create(CreateRequestApi::class.java)

    fun createRequest(request: Request): Observable<Response<CreateRequest>> {
        return requestApi.createRequest(request)
            .toObservable()
    }
}