package com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.presentation

import com.example.businesstripsapp.request_details_activity.models.Request
import com.example.businesstripsapp.request_details_activity.models.Trip


data class State(
    val isLoading: Boolean = false,
)

sealed class Effect {
    object ShowErrorLoading : Effect()
    object ShowErrorApproving : Effect()
    object ShowErrorSendingBack : Effect()
    object ShowErrorDeclining : Effect()
    data class AddTripId(val tripId: String) : Effect()
    data class ToTripDetailsActivity(val tripId: String) : Effect()
    data class OpenTicket(val ticket: String) : Effect()
    data class ShowRequestDetails(val request: Request) : Effect()
    data class UpdateStatus(val requestId: String): Effect()
    data class ShowCalendar(val date: String) : Effect()
    data class ShowMap(val address: String) : Effect()
}

sealed class Event {
    sealed class Ui : Event() {
        object Init : Ui()
        data class OnTripClicked(val tripId: String) : Ui()
        data class OnTicketClicked(val ticket: String) :Ui()
        data class ShowRequestDetails(val requestId: String) : Ui()
        data class OnApproveClicked(val requestId: String): Ui()
        data class OnSendBackClicked(val requestId: String): Ui()
        data class OnDeclineClicked(val requestId: String): Ui()
        data class OnCalendarClicked(val date: String) : Ui()
        data class OnMapClicked(val address : String) : Ui()
    }

    sealed class Internal : Event() {
        data class SuccessDetailsLoading(val request: Request) : Internal()
        object ErrorDetailsLoading : Internal()
        data class SuccessApproving(val tripId: String, val requestId: String) : Internal()
        object ErrorApproving : Internal()
        data class SuccessSendingBack(val requestId: String) : Internal()
        object ErrorSendingBack : Internal()
        data class SuccessDeclining(val requestId: String) : Internal()
        object ErrorDeclining : Internal()
    }
}

sealed class Command {
    data class LoadRequestDetails(val requestId: String) : Command()
    data class ApproveRequest(val requestId: String) : Command()
    data class DeclineRequest(val requestId: String) : Command()
    data class SendBackRequest(val requestId: String) : Command()

}