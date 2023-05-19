package com.example.businesstripsapp.request_edit_activity.presentation

import com.example.businesstripsapp.create_request_activity.domain.models.CreateDestination
import com.example.businesstripsapp.create_request_activity.domain.models.Destination
import com.example.businesstripsapp.create_request_activity.domain.models.Request


//Спросить про механизм Update

data class State(
    val isLoading: Boolean = false
)

sealed class Effect {
    object ShowErrorCreateRequest : Effect()
    object ShowErrorCreateDestination : Effect()
    object ShowErrorNetwork : Effect()
    object ToRequestsActivity: Effect()
    object ShowCalendarStartDate : Effect()
    object ShowCalendarEndDate : Effect()
    data class DestinationCreated(val createDestination: CreateDestination) : Effect()
}

sealed class Event {
    sealed class Ui : Event() {
        object Init : Ui()
        object OnBackClicked : Ui()
        data class OnCalendarStartDateClicked(val date: String) : Ui()
        data class OnCalendarEndDateClicked(val date: String) : Ui()
        data class OnCreateClicked(var destination: Destination) : Ui()
        data class OnDestinationCreated(var request: Request) : Ui()
    }

    sealed class Internal : Event() {
        object SuccessEditRequest : Internal()
        object ErrorEditRequest : Internal()
        data class SuccessCreateDestination(val createDestination: CreateDestination) : Internal()
        object ErrorCreateDestination : Internal()
        object ErrorNetwork : Internal()
    }
}

sealed class Command {
    data class EditRequest(var request: Request) : Command()
    data class CreateDestination(var destination: Destination) : Command()
}