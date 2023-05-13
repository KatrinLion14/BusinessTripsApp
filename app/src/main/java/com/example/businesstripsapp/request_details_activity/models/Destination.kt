package com.example.businesstripsapp.request_details_activity.models

data class Destination(
    val id: String,
    val description: String,
    val office: Office,
    val seatPlace: String
)