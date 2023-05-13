package com.example.businesstripsapp.requests_activity.requests_fragment.outgoing_requests_fragment.models

data class Destination(
    val id: String,
    val description: String,
    val office: Office,
    val seatPlace: String
)