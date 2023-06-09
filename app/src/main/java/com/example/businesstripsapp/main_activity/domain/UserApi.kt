package com.example.businesstripsapp.main_activity.domain

import com.example.businesstripsapp.main_activity.domain.models.CreateUser
import com.example.businesstripsapp.main_activity.domain.models.Notification
import com.example.businesstripsapp.main_activity.domain.models.Request
import com.example.businesstripsapp.main_activity.domain.models.Trip
import com.example.businesstripsapp.main_activity.domain.models.User
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

const val GET_USER_URL: String = "/users/{id}"
const val CREATE_USER_URL: String = "/users"
const val REQUESTS_URL: String = "/users/{id}/outgoing-requests-at/1"
const val TRIPS_URL: String = "/users/{id}/trips-at/1"
const val NOTIFICATIONS_URL: String = "/users/{id}/unwatched-notifications"

interface UserApi {
    @GET(GET_USER_URL)
    fun getUser(@Path("id") id: String): Single<Response<User>>

    @GET(REQUESTS_URL)
    fun getRequestsFirstPage(@Path("id") id: String): Single<Response<List<Request>>>

    @GET(TRIPS_URL)
    fun getTripsFirstPage(@Path("id") id: String): Single<Response<List<Trip>>>

    @GET(NOTIFICATIONS_URL)
    fun getNotifications(@Path("id") id: String): Single<Response<List<Notification>>>

    @POST(CREATE_USER_URL)
    fun createUser(@Body user: User): Single<Response<CreateUser>>
}