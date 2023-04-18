package com.example.businesstripsapp.start_activity.presentation

import com.example.businesstripsapp.start_activity.models.User

data class State(
    val auth: Boolean = false
)

sealed class Effect {
    object ShowAuthError : Effect()
    data class ToMainActivity(val token: String) : Effect()
}

sealed class Event {
    sealed class Ui : Event() {
        object Init : Ui()
        data class ClickContinue(val email: String, val password: String)  : Ui()
    }

    sealed class Internal : Event() {
        data class SuccessAuth(val token: String) : Internal()
        object ErrorAuth : Internal()
    }
}

sealed class Command {
    data class Auth(val email: String, val password: String) : Command()
}