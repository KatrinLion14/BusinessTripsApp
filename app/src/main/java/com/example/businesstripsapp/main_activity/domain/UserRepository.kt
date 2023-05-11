package com.example.businesstripsapp.main_activity.domain

import com.example.businesstripsapp.main_activity.domain.models.Notification
import com.example.businesstripsapp.main_activity.domain.models.Request
import com.example.businesstripsapp.main_activity.domain.models.Trip
import com.example.businesstripsapp.main_activity.domain.models.User
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit

class UserRepository(retrofit: Retrofit) {
    private val userApi: UserApi = retrofit.create(UserApi::class.java)

    fun getUser(id: String): Observable<Response<User>> {
        return userApi.getUser(id)
            .toObservable()
    }

    fun getRequests(id: String): Observable<Response<List<Request>>> {
        return userApi.getRequests(id)
            .toObservable()
    }

    fun getTrips(id: String): Observable<Response<List<Trip>>> {
        return userApi.getTrips(id)
            .toObservable()
    }

    fun getNotifications(id: String): Observable<Response<List<Notification>>> {
        return userApi.getNotifications(id)
            .toObservable()
    }
}
