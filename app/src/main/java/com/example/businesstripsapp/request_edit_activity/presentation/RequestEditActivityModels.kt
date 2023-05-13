package com.example.businesstripsapp.request_edit_activity.presentation


// TODO
data class State(
    val isOutgoing: Boolean = false
)

sealed class Effect {
    object ToRequestsActivity : Effect()
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