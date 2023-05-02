package com.example.businesstripsapp.trips_history_activity.presentation

import vivid.money.elmslie.core.store.ElmStore
import vivid.money.elmslie.core.store.NoOpActor
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer


class Reducer :
    ScreenDslReducer<Event, Event.Ui, Event.Internal, State, Effect, Command>(Event.Ui::class, Event.Internal::class) {
    override fun Result.internal(event: Event.Internal) = when (event) {
        is Event.Internal.MakeTripList -> {
            state { copy(loading = true) }
            effects { +Effect.ShowAllTrips(event.tripsArray) }
        }
    }

    override fun Result.ui(event: Event.Ui) = when (event) {
        is Event.Ui.ClickBack -> {
            effects { +Effect.BackToTripsActivity }
        }
        is Event.Ui.ClickTrip -> {
            effects { +Effect.ToTripInformationActivity(event.tripId) }
        }
        is Event.Ui.Init -> {}
    }
}

fun storeFactory() = ElmStore(
    initialState = State(),
    reducer = Reducer(),
    actor = NoOpActor()
)