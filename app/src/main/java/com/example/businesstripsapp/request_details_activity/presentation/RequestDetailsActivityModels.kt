package com.example.businesstripsapp.request_details_activity.presentation

data class State(
    val value: String = ""
)

sealed class Effect {
    object ToPreviousActivity : Effect()
    data class ToRequestsEditActivity(val requestId: String) : Effect()
}

sealed class Event {
    sealed class Ui : Event() {
        object Init : Ui()
        object OnBackClicked : Ui()
        data class OnEditClicked(val requestId: String) : Ui()
    }

    sealed class Internal : Event() {}
}

sealed class Command {}