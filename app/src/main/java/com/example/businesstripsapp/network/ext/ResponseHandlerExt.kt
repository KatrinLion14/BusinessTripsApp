package com.example.businesstripsapp.network.ext

import retrofit2.Response

fun <T, E> Response<T>.statusCodeHandler(successHandler: (T) -> E, errorHandler: () -> E) : E {
    return when {
        this.isSuccessful -> successHandler(this.body()!!)
        else -> errorHandler()
    }
}