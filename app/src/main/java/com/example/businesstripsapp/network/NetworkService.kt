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

class NetworkService {
    companion object {
        private var networkService: NetworkService? = null

        val instance: NetworkService
            get() {
                if (networkService == null) {
                    networkService = NetworkService()
                }
                return networkService!!
            }
    }

    private val BASE_URL = "http://84.252.137.33:8080/"
    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private var retrofit: Retrofit? = null
    private var authRetrofit: Retrofit? = null

    fun getAuthService(): Retrofit {
        if (authRetrofit == null) {
            setAuthService()
        }
        return authRetrofit!!
    }

    private fun setAuthService() {
        authRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    fun getService(): Retrofit {
        return retrofit!!
    }

    fun setService(token: String) {
        val client = OkHttpClient.Builder()
            .addInterceptor(BasicAuthInterceptor(token))
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    fun destroyService() {
        retrofit = null
    }
}
