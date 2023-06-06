package com.example.businesstripsapp.request_edit_activity.domain.models

import java.util.Date

data class RequestUpdate (
    val id: String?,
    val description: String?,
    val workerId: String,
    val destinationId: String?,
    val comment: String?,
    val startDate: Date?,
    val endDate: Date?,
    val ticketsUrl: String?
)