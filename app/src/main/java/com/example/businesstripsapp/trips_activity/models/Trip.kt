package com.example.businesstripsapp.trips_activity.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Trip (
    val id: String,
    val tripStatus: String,
    val destinationCity: String,
    val tripDate: String,

) : Parcelable