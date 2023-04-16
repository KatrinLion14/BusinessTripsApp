package com.example.businesstripsapp.start_activity.domain

import com.example.businesstripsapp.start_activity.models.User
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit

class UserRepository(retrofit: Retrofit) {
    private val userApi: UserApi = retrofit.create(UserApi::class.java)

    fun getUser(id: String): Observable<Response<User>> {
        return userApi.getUser(id)
            .toObservable()
    }
}