package com.example.businesstripsapp.start_activity.domain

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

const val LOGIN_URL: String = "/api/login"

interface LoginApi {
    @POST(LOGIN_URL)
    fun loginUser(@Body login: Login): Single<Response<String>>
}

data class Login(
    private val email: String,
    private val password: String
)
