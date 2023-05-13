package com.example.businesstripsapp.request_details_activity.request_details_fragment.outgoing_request_details_fragment.presentation

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