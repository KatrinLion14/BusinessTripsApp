package com.example.businesstripsapp.request_create_activity.presentation

//TODO
data class State(
    val smth: Boolean = false
)

sealed class Effect {
    object ShowError : Effect()
    data class ToStartActivity(val userId: String) : Effect()
    data class ToRequestsHistoryActivity(val userId: String) : Effect()
    data class ToRequestDetailsActivity(val userId: String) : Effect()
    data class ToRequestCreateActivity(val userId: String) : Effect()
}

sealed class Event {
    sealed class Ui : Event() {
        object ClickBack : Ui()
        object ClickRequest : Ui()
        object ClickRequestHistory : Ui()
        object ClickCreateRequest : Ui()

    }

    sealed class Internal : Event() {

    }
}

sealed class Command {
    data class GetRequestDetails(val email: String, val password: String) : Command()
}