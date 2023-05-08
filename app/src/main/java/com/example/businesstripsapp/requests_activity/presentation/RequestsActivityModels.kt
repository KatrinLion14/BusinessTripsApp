package com.example.businesstripsapp.requests_activity.presentation

import com.example.businesstripsapp.requests_activity.models.Request

data class State(
    val isLoading: Boolean = false,
)

sealed class Effect {
    object ShowError : Effect()
    object ToMainActivity : Effect()
    object ToRequestsHistoryActivity : Effect()
    data class ToRequestDetailsActivity(val requestId: String) : Effect()
    object ToRequestCreateActivity : Effect()
    data class ShowAllRequests(val requestsArray: Array<Request>) : Effect()
}

sealed class Event {
    sealed class Ui : Event() {
        object Init : Ui()
        object OnBackClicked : Ui()
        data class OnRequestClicked(val requestId: String) : Ui()
        object OnRequestHistoryClicked : Ui()
        object OnCreateRequestClicked : Ui()
    }

    sealed class Internal : Event() {
        data class SuccessLoading(val requestsArray: Array<Request>) : Internal()
        object ErrorLoading : Internal()
    }
}

sealed class Command {
    data class LoadRequests(val userId: String) : Command()
}