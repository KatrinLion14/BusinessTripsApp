package com.example.businesstripsapp.main_activity.profile_fragment.presentation

import com.example.businesstripsapp.main_activity.domain.models.User

data class State(
    val loading: Boolean = false,
    val user: User? = null
)

sealed class Effect {
    object ShowErrorNetwork : Effect()
    object ShowLoadingError : Effect()
    data class ToSubordinateDetails(val subordinate: User)  : Effect()
    object ExitToStart : Effect()
}

sealed class Event {
    sealed class Ui : Event() {
        data class Init(val id: String) : Ui()
        data class ClickSubordinate(val subordinate: User) : Ui()
        object CLickExit : Ui()
    }

    sealed class Internal : Event() {
        data class SuccessLoad(val user: User) : Internal()
        object ErrorNetwork : Internal()
        object ErrorLoading : Internal()
    }
}

sealed class Command {
    class LoadProfile(val id: String) : Command()
}