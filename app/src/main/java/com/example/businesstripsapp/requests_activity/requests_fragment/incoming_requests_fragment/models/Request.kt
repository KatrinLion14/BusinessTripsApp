package com.example.businesstripsapp.requests_activity.requests_fragment.incoming_requests_fragment.models


data class Request (
    val id : String,
    val requestStatus : String,
    val workerFirstName : String,
    val workerSecondName : String,
    val destination : Destination,
    val startDate : String,
    val endDate : String
    )