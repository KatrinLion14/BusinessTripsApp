package com.example.businesstripsapp.create_office_activity.presentation

import com.example.businesstripsapp.create_office_activity.domain.models.Office

data class State(
    val loading: Boolean = false
)

sealed class Effect {
    object ShowErrorCreateOffice : Effect()
    object ShowErrorNetwork : Effect()
    object ReturnToHome: Effect()
}

sealed class Event {
    sealed class Ui : Event() {
        object Init : Ui()
        data class CreateClick(val office: Office) : Ui()
    }

    sealed class Internal : Event() {
        object SuccessCreateOffice : Internal()
        object ErrorCreateOffice : Internal()
        object ErrorNetwork : Internal()
    }
}

sealed class Command {
    data class CreateOffice(var office: Office) : Command()
}