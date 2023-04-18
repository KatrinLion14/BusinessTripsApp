package com.example.businesstripsapp.network

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class BasicAuthInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request().newBuilder()
            .header("Authorization", "Bearer $token").build()
        return chain.proceed(request)
    }
}

class NetworkService(private val token: String? = null) {
    private val BASE_URL = "http://10.0.2.2:8080/"
    private val gson = GsonBuilder()
        .setLenient()
        .create()
    val retrofit: Retrofit
        get() {
            val result: Retrofit
            if (token != null) {
                val client = OkHttpClient.Builder()
                    .addInterceptor(BasicAuthInterceptor(token))
                    .build()

                result = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            } else {
                result = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            }
            return result
        }
}
