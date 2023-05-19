package com.example.businesstripsapp.request_edit_activity.domain

import com.example.businesstripsapp.request_edit_activity.domain.models.Request
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit

class EditRequestRepository(retrofit: Retrofit)  {
    private val requestApi: EditRequestApi = retrofit.create(EditRequestApi::class.java)

    fun editRequest(request: Request): Observable<Response<Void>> {
        return requestApi.editRequest(request)
            .toObservable()
    }
}