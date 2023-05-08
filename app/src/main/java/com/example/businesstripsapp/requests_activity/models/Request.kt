package com.example.businesstripsapp.requests_activity.models

data class Request (
    val requestId: String,
    val employee: String,
    val requestStatus: String,
    val destinationCity: String,
    val date: String
)