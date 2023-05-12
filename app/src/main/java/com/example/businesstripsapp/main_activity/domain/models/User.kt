package com.example.businesstripsapp.main_activity.domain.models

import java.io.Serializable

data class User(
    val id: String? = null,
    val email: String,
    val password: String,
    val firstName: String,
    val secondName: String,
    val subordinates: List<User>? = null,
    val userRole: String
) : Serializable
