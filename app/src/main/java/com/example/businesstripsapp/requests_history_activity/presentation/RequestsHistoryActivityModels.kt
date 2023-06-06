package com.example.businesstripsapp.requests_history_activity.presentation

data class State(
    val value: String = ""
)

sealed class Effect {
    object ToPreviousActivity : Effect()
}

sealed class Event {
    sealed class Ui : Event() {
        object Init : Ui()
        object OnBackClicked : Ui()
    }

    sealed class Internal : Event() { }
}

sealed class Command { }