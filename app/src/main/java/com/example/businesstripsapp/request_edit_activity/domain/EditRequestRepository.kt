package com.example.businesstripsapp.request_edit_activity.domain

import com.example.businesstripsapp.request_edit_activity.domain.models.DestinationUpdate
import com.example.businesstripsapp.request_edit_activity.domain.models.Request
import com.example.businesstripsapp.request_edit_activity.domain.models.RequestUpdate
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit

class EditRequestRepository(retrofit: Retrofit)  {
    private val requestApi: EditRequestApi = retrofit.create(EditRequestApi::class.java)

    fun getRequest(id: String): Observable<Response<Request>> {
        return requestApi.getRequest(id).toObservable()
    }

    fun editRequest(requestUpdate: RequestUpdate): Observable<Response<Void>> {
        return requestApi.editRequest(requestUpdate)
            .toObservable()
    }

    fun editDestination(destinationUpdate: DestinationUpdate): Observable<Response<Void>> {
        return requestApi.editDestination(destinationUpdate)
            .toObservable()
    }
}