package com.example.businesstripsapp.request_edit_activity.presentation

import com.example.businesstripsapp.request_edit_activity.domain.models.Destination
import com.example.businesstripsapp.request_edit_activity.domain.models.DestinationUpdate
import com.example.businesstripsapp.request_edit_activity.domain.models.Request
import com.example.businesstripsapp.request_edit_activity.domain.models.RequestUpdate


data class State(
    val isLoading: Boolean = false
)

sealed class Effect {
    object ShowErrorLoading : Effect()
    data class ShowRequestDetails(val request: Request) : Effect()
    object ShowErrorEditRequest : Effect()
    object ShowErrorEditDestination : Effect()
    object ShowErrorNetwork : Effect()
    object ToRequestDetailsActivity: Effect()
    object ShowDialog: Effect()
    object ShowCalendarStartDate : Effect()
    object ShowCalendarEndDate : Effect()
    object DestinationEdited : Effect()
}

sealed class Event {
    sealed class Ui : Event() {
        object Init : Ui()
        data class GetRequestDetails(val requestId: String) : Ui()
        object OnBackClicked : Ui()
        object OnExitClicked : Ui()
        data class OnCalendarStartDateClicked(val date: String) : Ui()
        data class OnCalendarEndDateClicked(val date: String) : Ui()
        data class OnSaveClicked(var destinationUpdate: DestinationUpdate) : Ui()
        data class OnDestinationEdited(var requestUpdate: RequestUpdate) : Ui()
    }

    sealed class Internal : Event() {
        data class SuccessDetailsLoading(val request: Request) : Internal()
        object ErrorDetailsLoading : Internal()
        object SuccessEditRequest : Internal()
        object ErrorEditRequest : Internal()
        object SuccessEditDestination : Internal()
        object ErrorEditDestination : Internal()
        object ErrorNetwork : Internal()
    }
}

sealed class Command {
    data class LoadRequestDetails(val requestId: String) : Command()
    data class EditRequest(var requestUpdate: RequestUpdate) : Command()
    data class EditDestination(var destinationUpdate: DestinationUpdate) : Command()
}