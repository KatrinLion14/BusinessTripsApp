package com.example.businesstripsapp.requests_activity.requests_fragment.outgoing_requests_fragment.models

import java.util.Date

data class Request (
    val id : String,
    val requestStatus : String,
    val destination : Destination,
    val startDate : String,
    val endDate : String,
)