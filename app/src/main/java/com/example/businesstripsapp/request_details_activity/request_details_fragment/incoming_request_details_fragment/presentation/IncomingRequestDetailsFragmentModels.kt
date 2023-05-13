package com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.presentation

import com.example.businesstripsapp.request_details_activity.models.Request
import com.example.businesstripsapp.request_details_activity.models.Trip


data class State(
    val isLoading: Boolean = false,
)

sealed class Effect {
    object ShowError : Effect()
    data class ToTripDetailsActivity(val tripId: String) : Effect()
    data class OpenTicket(val ticket: String) : Effect()
    data class ShowRequestDetails(val request: Request) : Effect()
    data class ChangeStatusApproved(val requestId: String): Effect()
    data class ChangeStatusPending(val requestId: String): Effect()
    data class ChangeStatusDeclined(val requestId: String): Effect()
}

sealed class Event {
    sealed class Ui : Event() {
        object Init : Ui()
        data class OnTripClicked(val tripId: String) : Ui()
        data class OnTicketClicked(val ticket: String) :Ui()
        data class ShowRequestDetails(val requestId: String) : Ui()
        data class OnApproveClicked(val requestId: String): Ui()
        data class OnReturnClicked(val requestId: String): Ui()
        data class OnDeclineClicked(val requestId: String): Ui()
    }

    sealed class Internal : Event() {
        data class SuccessLoading(val request: Request) : Internal()
        object ErrorLoading : Internal()
    }
}

sealed class Command {
    data class LoadRequestDetails(val requestId: String) : Command()
}