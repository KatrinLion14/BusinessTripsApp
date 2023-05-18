package com.example.businesstripsapp.create_trip_activity.domain.models

data class Trip (
    val id: String? = null,
    val tripStatus : String?,
    val accomodationId : String?,
    val destinationId : String?,
    val requestId : String?
)