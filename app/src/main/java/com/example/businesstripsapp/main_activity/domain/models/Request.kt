package com.example.businesstripsapp.main_activity.domain.models

import com.example.businesstripsapp.main_activity.domain.models.constant.RequestStatus

data class Request(
    val destination: Destination,
    val startDate: String,
    val requestStatus: RequestStatus,
    val endDate: String
) {
    data class Destination(
        val office: Office
    )

    data class Office(
        val address: String
    )
}
