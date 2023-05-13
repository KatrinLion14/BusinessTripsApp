package com.example.businesstripsapp.request_details_activity.models

data class Trip (
    val id: String,
    val tripStatus: String,
    val accommodation: Accommodation,
    val destination: Destination,
    val requestId: String,
    val startDate: String,
    val endDate: String
)