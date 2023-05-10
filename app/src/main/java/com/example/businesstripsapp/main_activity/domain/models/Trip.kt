package com.example.businesstripsapp.main_activity.domain.models

import com.example.businesstripsapp.main_activity.domain.models.constant.TripStatus

data class Trip(
    val destination: Destination,
    val startDate: String,
    val requestStatus: TripStatus,
    val endDate: String
) {
    data class Destination(
        val office: Office
    )

    data class Office(
        val address: String
    )
}
