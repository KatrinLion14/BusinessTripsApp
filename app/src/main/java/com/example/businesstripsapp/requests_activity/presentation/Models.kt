package com.example.businesstripsapp.requests_activity.presentation

data class State(
    val requestsList : List<String> = mutableListOf(),
    val isLoading: Boolean = false
)

sealed class Effect {
    object ShowError : Effect()
    object ToStartActivity : Effect()
    object ToRequestsHistoryActivity : Effect()
    object ToRequestDetailsActivity : Effect()
    object ToRequestCreateActivity : Effect()
}

sealed class Event {
    sealed class Ui : Event() {
        object OnBackClicked : Ui()
        object ClickRequest : Ui()
        object ClickRequestHistory : Ui()
        object ClickCreateRequest : Ui()
        object ScrollRequestsList : Ui()
    }

    sealed class Internal : Event() {
        object SuccessLoading : Internal()
        object ErrorLoading : Internal()
    }
}

sealed class Command {
    data class LoadRequestsInfo(val userId: String) : Command()
}