package com.example.businesstripsapp.requests_activity.requests_fragment.incoming_requests_fragment.domain.models

data class Destination(
    val id: String,
    val description: String,
    val office: Office,
    val seatPlace: String
)