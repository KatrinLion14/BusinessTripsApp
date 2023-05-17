package com.example.businesstripsapp.start_activity.domain

import com.example.businesstripsapp.start_activity.domain.models.Login
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

const val LOGIN_URL: String = "/api/login"

interface LoginApi {
    @POST(LOGIN_URL)
    fun loginUser(@Body login: Login): Single<Response<String>>
}
