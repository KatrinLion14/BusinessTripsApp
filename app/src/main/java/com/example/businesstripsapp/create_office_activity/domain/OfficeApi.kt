package com.example.businesstripsapp.create_office_activity.domain

import com.example.businesstripsapp.create_office_activity.domain.models.CreateOffice
import com.example.businesstripsapp.create_office_activity.domain.models.Office
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

const val CREATE_USER_URL: String = "/offices"

interface OfficeApi {
    @POST(CREATE_USER_URL)
    fun createOffice(@Body office: Office): Single<Response<CreateOffice>>
}