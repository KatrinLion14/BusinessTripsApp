package com.example.businesstripsapp.requests_history_activity.presentation

data class State(
    val value: String = ""
)

sealed class Effect {
    object ToPreviousActivity : Effect()
    object ToRequestsHistoryActivity : Effect()
    object ToRequestCreateActivity : Effect()
}

sealed class Event {
    sealed class Ui : Event() {
        object Init : Ui()
        object OnBackClicked : Ui()
        object OnRequestHistoryClicked : Ui()
        object OnCreateRequestClicked : Ui()
    }

    sealed class Internal : Event() { }
}

sealed class Command { }