package com.example.businesstripsapp.request_details_activity.request_details_fragment.outgoing_request_details_fragment.presentation

import com.example.businesstripsapp.request_details_activity.request_details_fragment.outgoing_request_details_fragment.domain.models.Request

data class State(
    val isLoading: Boolean = false
)

sealed class Effect {
    object ShowErrorLoading : Effect()
    data class ShowRequestDetails(val request: Request) : Effect()
    data class ShowCalendar(val date: String) : Effect()
    data class ShowMap(val address: String) : Effect()
}

sealed class Event {
    sealed class Ui : Event() {
        object Init : Ui()
        data class GetRequestDetails(val requestId: String) : Ui()
        data class OnCalendarClicked(val date: String) : Ui()
        data class OnMapClicked(val address : String) : Ui()
    }

    sealed class Internal : Event() {
        data class SuccessDetailsLoading(val request: Request) : Internal()
        object ErrorDetailsLoading : Internal()
    }
}

sealed class Command {
    data class LoadRequestDetails(val requestId: String) : Command()
}