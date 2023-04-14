package com.example.businesstripsapp.start_activity.presentation

data class State(
    val auth: Boolean = false
)

sealed class Effect {
    object ShowAuthError : Effect()
    data class ToManActivity(val userId: String) : Effect()
}

sealed class Event {
    sealed class Ui : Event() {
        object Init : Ui()
        data class ClickStart(val user: String)  : Ui()
    }

    sealed class Internal : Event() {
        data class SuccessAuth(val user: String) : Internal()
        object ErrorAuth : Internal()
    }
}

sealed class Command {
    data class Auth(val email: String, val password: String) : Command()
}