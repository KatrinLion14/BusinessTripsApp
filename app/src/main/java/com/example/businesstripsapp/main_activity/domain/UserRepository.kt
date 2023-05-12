package com.example.businesstripsapp.main_activity.domain

import com.example.businesstripsapp.main_activity.domain.models.CreateUser
import com.example.businesstripsapp.main_activity.domain.models.Notification
import com.example.businesstripsapp.main_activity.domain.models.Request
import com.example.businesstripsapp.main_activity.domain.models.Trip
import com.example.businesstripsapp.main_activity.domain.models.User
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.toObservable
import retrofit2.Response
import retrofit2.Retrofit

class UserRepository(retrofit: Retrofit) {
    private val userApi: UserApi = retrofit.create(UserApi::class.java)

    fun getUser(id: String): Observable<Response<User>> {
        return userApi.getUser(id)
            .toObservable()
    }

    fun getRequestsFirstPage(id: String): Observable<Response<List<Request>>> {
        return userApi.getRequestsFirstPage(id)
            .toObservable()
    }

    fun getTripsFirstPage(id: String): Observable<Response<List<Trip>>> {
        return userApi.getTripsFirstPage(id)
            .toObservable()
    }

    fun getNotifications(id: String): Observable<Response<List<Notification>>> {
        return userApi.getNotifications(id)
            .toObservable()
    }

    fun createUser(user: User): Observable<Response<CreateUser>> {
        return userApi.createUser(user)
            .toObservable()
    }
}
