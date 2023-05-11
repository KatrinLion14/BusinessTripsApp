package com.example.businesstripsapp.main_activity.domain.models

data class User(
    val id: String,
    val email: String,
    val password: String,
    val firstName: String,
    val secondName: String,
    val userRole: String
)
