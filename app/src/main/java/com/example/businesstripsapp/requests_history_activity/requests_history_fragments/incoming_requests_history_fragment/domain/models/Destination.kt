package com.example.businesstripsapp.requests_history_activity.requests_history_fragments.incoming_requests_history_fragment.domain.models

data class Destination(
    val id: String,
    val description: String,
    val office: Office,
    val seatPlace: String
)