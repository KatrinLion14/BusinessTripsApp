package com.example.businesstripsapp.request_edit_activity.domain

import com.example.businesstripsapp.request_edit_activity.domain.models.Request
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

const val EDIT_REQUEST_URL: String = "/requests"

interface EditRequestApi {
    @PUT(EDIT_REQUEST_URL)
    fun editRequest(@Body request: Request): Single<Response<Void>>
}