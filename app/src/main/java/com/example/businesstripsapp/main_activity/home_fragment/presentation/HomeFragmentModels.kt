package com.example.businesstripsapp.main_activity.home_fragment.presentation

import com.example.businesstripsapp.main_activity.domain.models.Request
import com.example.businesstripsapp.main_activity.domain.models.Trip

data class State(
    val loading: Boolean = false,
    val requests: List<Request> = listOf(),
    val trips: List<Trip> = listOf()
)

sealed class Effect {
    object ShowErrorNetwork : Effect()
    object ShowLoadingError : Effect()
    object ToNotificationActivity : Effect()
    object ToTripsActivity : Effect()
    object ToRequestsActivity : Effect()
    object ToCreateUserActivity : Effect()
    object ToCreateOfficeActivity : Effect()
}

sealed class Event {
    sealed class Ui : Event() {
        data class Init(var id: String) : Ui()
        data class PullToRefresh(var id: String) : Ui()
        object ClickRequests : Ui()
        object ClickTrips : Ui()
        object ClickNotifications : Ui()
        object CLickCreateUser : Ui()
        object ClickCreateOffice : Ui()
    }

    sealed class Internal : Event() {
        data class SuccessLoadRequests(var requests: List<Request>) : Internal()
        data class SuccessLoadTrips(var trips: List<Trip>) : Internal()
        object ErrorNetwork : Internal()
        object ErrorLoading : Internal()
    }
}

sealed class Command {
    class LoadRequests(var id: String) : Command()
    class LoadTrips(var id: String) : Command()
}