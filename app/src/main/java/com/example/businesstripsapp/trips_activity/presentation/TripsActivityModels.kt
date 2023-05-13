package com.example.businesstripsapp.trips_activity.presentation

import com.example.businesstripsapp.trips_activity.models.Trip

data class State(
    val loading: Boolean = false
)

sealed class Effect {
    object ShowLoadingError : Effect()
    object BackToMainActivity : Effect()
    object ToTripsHistoryActivity: Effect()
    data class ToTripInformationActivity(val tripId: String): Effect()
    data class ShowAllTrips(val tripsArray: Array<Trip>) : Effect()
}

sealed class Event {
    sealed class Ui : Event() {
        object Init : Ui()
        object ClickBack : Ui()
        object ClickHistory : Ui()
        data class ClickTrip(val tripId: String) : Ui()
        data class ShowTripsList(val userId: String) : Ui()
    }

    sealed class Internal : Event() {
        data class SuccessLoading(val tripArray: Array<Trip>) : Internal()
        object ErrorLoading : Internal()
    }
}

sealed class Command {
    data class LoadAllTrips(val userId: String): Command()
}