package com.example.businesstripsapp.trip_info_activity.presentation

import com.example.businesstripsapp.trip_info_activity.models.Trip


data class State(
    val loading: Boolean = false
)

sealed class Effect {
    object ShowLoadingError : Effect()
    object BackToPreviousActivity : Effect()
    data class ShowTripInfo(val trip: Trip) : Effect()
}

sealed class Event {
    sealed class Ui : Event() {
        object Init : Ui()
        object ClickBack : Ui()
        data class ShowTripInfo(val tripId : String) : Ui()
    }

    sealed class Internal : Event() {
        data class SuccessLoading(val trip: Trip) : Internal()
        object ErrorLoading : Internal()
    }
}

sealed class Command {
    data class LoadTrip(val tripId: String) : Command()
}