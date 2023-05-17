package com.example.businesstripsapp.start_activity.domain

import com.example.businesstripsapp.start_activity.domain.models.Login
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit

class LoginRepository(retrofit: Retrofit) {
    private val loginApi: LoginApi = retrofit.create(LoginApi::class.java)

    fun loginUser(email: String, password: String): Observable<Response<String>> {
        return loginApi.loginUser(Login(email, password))
            .toObservable()
    }
}
