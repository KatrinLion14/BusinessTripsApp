package com.example.businesstripsapp.create_trip_activity.presentation

import com.example.businesstripsapp.create_accommodation_activity.domain.models.Accommodation
import com.example.businesstripsapp.create_trip_activity.domain.models.Trip
import com.example.businesstripsapp.request_details_activity.models.Destination

data class State(
    val isLoading: Boolean = false
)

sealed class Effect {
    object ShowErrorCreateTrip : Effect()
    object ShowErrorNetwork : Effect()
    object ToTripDetails: Effect()
}

sealed class Event {
    sealed class Ui : Event() {
        object Init : Ui()
        data class OnCreateClicked(val trip: Trip) : Ui()
        data class OnDestinationClicked(val destination: Destination) : Ui()
        data class OnAccommodationClicked(val accommodation: Accommodation) : Ui()
    }

    sealed class Internal : Event() {
        object SuccessCreateTrip : Internal()
        object ErrorCreateTrip : Internal()
        object ErrorNetwork : Internal()
    }
}

sealed class Command {
    data class CreateTrip(var trip: Trip) : Command()
}