package com.example.businesstripsapp.create_request_activity.domain.models

import java.util.Date

data class Request(
    val id: String?,
    val description: String?,
    val workerId: String,
    val destinationId: String?,
    val comment: String?,
    val startDate: Date?,
    val endDate: Date?,
    val ticketsUrl: String?
)