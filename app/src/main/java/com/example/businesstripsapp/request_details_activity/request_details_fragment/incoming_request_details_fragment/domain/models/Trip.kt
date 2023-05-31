package com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.domain.models

data class Trip (
    val id: String? = null,
    val tripStatus : String?,
    val accommodationId : String?,
    val destinationId : String?,
    val requestId : String?
)