package com.example.businesstripsapp.request_details_activity.models

import java.util.Date

data class Request (
    val id : String,
    val requestStatus : String,
    val description : String,
    val workerFirstName : String,
    val workerSecondName : String,
    val workerEmail : String,
    val destination : Destination,
    val comment : String,
    val approverId : String, // UUID
    val startDate : Date,
    val endDate : Date,
    val ticketsUrl : String,  // URL
)