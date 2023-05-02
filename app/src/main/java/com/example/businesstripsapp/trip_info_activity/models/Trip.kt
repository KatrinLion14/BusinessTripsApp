package com.example.businesstripsapp.trip_info_activity.models

data class Trip (
    val id: String,
    val tripStatus: String,
    val accommodation: Accommodation,
    val destination: Destination,
    val requestId: String

)