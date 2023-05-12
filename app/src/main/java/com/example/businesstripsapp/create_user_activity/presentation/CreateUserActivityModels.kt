package com.example.businesstripsapp.create_user_activity.presentation

import com.example.businesstripsapp.main_activity.domain.models.User

data class State(
    val loading: Boolean = false
)

sealed class Effect {
    object ShowErrorCreateUser : Effect()
    object ShowErrorNetwork : Effect()
    object ReturnToHome: Effect()
    object ShowErrorGetSubordinates : Effect()
}

sealed class Event {
    sealed class Ui : Event() {
        object Init : Ui()
        data class CreateClick(val user: User, val id: String) : Ui()
    }

    sealed class Internal : Event() {
        object SuccessCreateUser : Internal()
        object ErrorCreateUser : Internal()
        data class SuccessGetSubordinates(val user: User) : Internal()
        data class ErrorGetSubordinates(val user: User) : Internal()
        object ErrorNetwork : Internal()
    }
}

sealed class Command {
    data class GetSubordinates(val user: User, val id: String) : Command()
    data class CreateUser(var user: User) : Command()
}