package com.example.businesstripsapp.main_activity.domain.models

import com.example.businesstripsapp.main_activity.domain.models.constant.RequestStatus
import java.io.Serializable

data class Request(
    val destination: Destination,
    val startDate: String,
    val requestStatus: RequestStatus,
    val endDate: String,
    val workerFirstName: String,
    val workerSecondName: String
) : Serializable {
    data class Destination(
        val office: Office
    ) : Serializable

    data class Office(
        val address: String
    ) : Serializable
}
