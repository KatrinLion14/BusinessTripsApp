package com.example.businesstripsapp.main_activity.domain.models.constant

import com.google.gson.annotations.SerializedName

enum class TripStatus {
    @SerializedName("pending")
    PENDING,
    @SerializedName("completed")
    COMPLETED,
    @SerializedName("cancelled")
    CANCELLED
}