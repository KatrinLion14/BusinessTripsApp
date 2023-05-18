package com.example.businesstripsapp.create_accommodation_activity.presentation

import com.example.businesstripsapp.create_accommodation_activity.domain.models.Accommodation
import com.example.businesstripsapp.create_accommodation_activity.domain.models.CreateAccommodation

data class State(
    val isLoading: Boolean = false
)

sealed class Effect {
    object ShowErrorCreateAccommodation : Effect()
    object ShowErrorNetwork : Effect()
    data class ToPreviousActivity(val createAccommodation: CreateAccommodation): Effect()
}

sealed class Event {
    sealed class Ui : Event() {
        object Init : Ui()
        data class CreateClick(val accommodation: Accommodation) : Ui()
    }

    sealed class Internal : Event() {
        data class SuccessCreateAccommodation(val createAccommodation: CreateAccommodation) : Internal()
        object ErrorCreateAccommodation : Internal()
        object ErrorNetwork : Internal()
    }
}

sealed class Command {
    data class CreateAccommodation(val accommodation: Accommodation) : Command()
}