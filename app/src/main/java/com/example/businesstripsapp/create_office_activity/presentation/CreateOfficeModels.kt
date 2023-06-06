package com.example.businesstripsapp.create_office_activity.presentation

import com.example.businesstripsapp.create_office_activity.domain.models.CreateOffice
import com.example.businesstripsapp.create_office_activity.domain.models.Office

data class State(
    val loading: Boolean = false,
)

sealed class Effect {
    object ShowErrorCreateOffice : Effect()
    object ShowErrorNetwork : Effect()
    object ReturnToHome: Effect()
    data class SaveOfficeId(val office: Office): Effect()
}

sealed class Event {
    sealed class Ui : Event() {
        object Init : Ui()
        data class CreateClick(val office: Office) : Ui()
    }

    sealed class Internal : Event() {
        data class SuccessCreateOffice(val createOffice: CreateOffice, val office: Office) : Internal()
        object ErrorCreateOffice : Internal()
        object ErrorNetwork : Internal()
    }
}

sealed class Command {
    data class CreateOffice(var office: Office) : Command()
}