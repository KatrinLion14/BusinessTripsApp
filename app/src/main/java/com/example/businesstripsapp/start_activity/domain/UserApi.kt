package com.example.businesstripsapp.start_activity.domain

import com.example.businesstripsapp.start_activity.models.User
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

const val USER_URL: String = "/users/{id}"

interface UserApi {
    @GET(USER_URL)
    fun getUser(@Path("id") id: String): Single<Response<User>>
}