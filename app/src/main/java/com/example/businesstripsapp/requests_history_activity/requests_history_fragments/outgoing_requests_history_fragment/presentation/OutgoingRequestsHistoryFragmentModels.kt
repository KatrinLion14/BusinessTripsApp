package com.example.businesstripsapp.requests_history_activity.requests_history_fragments.outgoing_requests_history_fragment.presentation

import com.example.businesstripsapp.requests_history_activity.requests_history_fragments.outgoing_requests_history_fragment.domain.models.Request

data class State(
    val isLoading: Boolean = false,
)

sealed class Effect {
    object ShowError : Effect()
    data class ToRequestDetailsActivity(val requestId: String) : Effect()
    data class ShowAllRequests(val requestsArray: Array<Request>) : Effect()
}

sealed class Event {
    sealed class Ui : Event() {
        object Init : Ui()
        data class OnRequestClicked(val requestId: String) : Ui()
        data class ShowRequests(val userId: String) : Ui()
    }

    sealed class Internal : Event() {
        data class SuccessLoading(val requestsArray: Array<Request>) : Internal()
        object ErrorLoading : Internal()
    }
}

sealed class Command {
    data class LoadRequests(val userId: String) : Command()
}