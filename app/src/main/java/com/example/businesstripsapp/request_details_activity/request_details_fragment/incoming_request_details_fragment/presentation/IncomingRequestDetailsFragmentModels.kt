package com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.presentation

import com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.domain.models.Accommodation
import com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.domain.models.CreateAccommodation
import com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.domain.models.CreateTrip
import com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.domain.models.Request
import com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.domain.models.RequestChangeStatus
import com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.domain.models.Trip


data class State(
    val isLoading: Boolean = false,
)

sealed class Effect {
    object ShowErrorLoading : Effect()
    object ShowErrorApproving : Effect()
    object ShowErrorSendingBack : Effect()
    object ShowErrorDeclining : Effect()
    object ShowErrorCreateAccommodation : Effect()
    //object ShowErrorCreateTrip : Effect()
    data class ShowRequestDetails(val request: Request) : Effect()
    data class ShowCalendar(val date: String) : Effect()
    data class ShowMap(val address: String) : Effect()
    //object CloseDialog : Effect()
    object ShowDialog : Effect()
    object ShowSuccessStatusUpdating : Effect()
    data class AccommodationCreated(val createAccommodation: CreateAccommodation) : Effect()
    //data class TripCreated(val createTrip: CreateTrip) : Effect()
}

sealed class Event {
    sealed class Ui : Event() {
        object Init : Ui()
        data class ShowRequestDetails(val requestId: String) : Ui()
        object OnApproveClicked: Ui()
        data class OnSendBackClicked(val requestId: String, val requestChangeStatus: RequestChangeStatus): Ui()
        data class OnDeclineClicked(val requestId: String, val requestChangeStatus: RequestChangeStatus): Ui()
        data class OnCalendarClicked(val date: String) : Ui()
        data class OnMapClicked(val address : String) : Ui()
        data class OnCreateAccommodationClicked(val accommodation: Accommodation) : Ui()
        data class OnAccommodationCreated(val requestId: String, val requestChangeStatus: RequestChangeStatus) : Ui()
        //data class OnTripCreated(val requestId: String, val requestChangeStatus: RequestChangeStatus) : Ui()
        //object OnCancelClicked : Ui()
    }

    sealed class Internal : Event() {
        data class SuccessDetailsLoading(val request: Request) : Internal()
        object ErrorDetailsLoading : Internal()
        data class SuccessApproving(val requestId: String) : Internal()
        object ErrorApproving : Internal()
        data class SuccessSendingBack(val requestId: String) : Internal()
        object ErrorSendingBack : Internal()
        data class SuccessDeclining(val requestId: String) : Internal()
        object ErrorDeclining : Internal()
        data class SuccessCreateAccommodation(val createAccommodation: CreateAccommodation) : Internal()
        object ErrorCreateAccommodation : Internal()
        //data class SuccessCreateTrip(val createTrip: CreateTrip) : Internal()
        //object ErrorCreateTrip : Internal()
    }
}

sealed class Command {
    data class LoadRequestDetails(val requestId: String) : Command()
    data class ApproveRequest(val requestId: String, val requestChangeStatus: RequestChangeStatus) : Command()
    data class DeclineRequest(val requestId: String, val requestChangeStatus: RequestChangeStatus) : Command()
    data class SendBackRequest(val requestId: String, val requestChangeStatus: RequestChangeStatus) : Command()
    data class CreateAccommodation(val accommodation: Accommodation) : Command()
    //data class CreateTrip(val trip: Trip) : Command()

}