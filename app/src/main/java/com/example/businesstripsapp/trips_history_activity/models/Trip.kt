package com.example.businesstripsapp.trips_history_activity.models



data class Trip (
    val id: String,
    val tripStatus: String,
    val accommodation: Accommodation,
    val destination: Destination,
    val requestId: String,
    val startDate: String,
    val endDate: String
)