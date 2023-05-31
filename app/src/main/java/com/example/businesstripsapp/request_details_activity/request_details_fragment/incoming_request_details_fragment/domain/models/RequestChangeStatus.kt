package com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.domain.models

data class RequestChangeStatus (
    val approverId: String,
    val tripDTo: Trip?,
    val comment: String?
)