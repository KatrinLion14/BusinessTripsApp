package com.example.businesstripsapp.main_activity.domain.models.constant

import com.google.gson.annotations.SerializedName

enum class RequestStatus {
    @SerializedName("approved")
    APPROVED,
    @SerializedName("pending")
    PENDING,
    @SerializedName("awaitChanges")
    AWAIT_CHANGES,
    @SerializedName("declined")
    DECLINED
}