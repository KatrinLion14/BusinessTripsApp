package com.example.businesstripsapp.create_office_activity.domain

import com.example.businesstripsapp.create_office_activity.domain.models.CreateOffice
import com.example.businesstripsapp.create_office_activity.domain.models.Office
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit

class OfficeRepository(retrofit: Retrofit)  {
    private val officeApi: OfficeApi = retrofit.create(OfficeApi::class.java)

    fun createOffice(office: Office): Observable<Response<CreateOffice>> {
        return officeApi.createOffice(office)
            .toObservable()
    }
}