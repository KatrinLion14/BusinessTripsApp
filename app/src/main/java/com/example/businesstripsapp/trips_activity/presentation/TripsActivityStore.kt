package com.example.businesstripsapp.trips_activity.presentation

import com.example.businesstripsapp.network.NetworkService
import com.example.businesstripsapp.network.ext.statusCodeHandler
import com.example.businesstripsapp.trips_activity.domain.TripRepository
import io.reactivex.Observable
import vivid.money.elmslie.core.Actor
import vivid.money.elmslie.core.ElmStoreCompat
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

class Reducer :
    ScreenDslReducer<Event, Event.Ui, Event.Internal, State, Effect, Command>(Event.Ui::class, Event.Internal::class) {
    override fun Result.internal(event: Event.Internal) = when (event) {
        is Event.Internal.SuccessLoading -> {
            state { copy(loading = true) }
            effects { +Effect.ShowAllTrips(event.tripArray) }
        }
        is Event.Internal.ErrorLoading -> {
            effects { +Effect.ShowLoadingError }
        }
    }

    override fun Result.ui(event: Event.Ui) = when (event) {
        is Event.Ui.ClickBack -> {
            effects { +Effect.BackToMainActivity }
        }
        is Event.Ui.ClickHistory -> {
            effects { +Effect.ToTripsHistoryActivity(event.tripsArray) }
        }
        is Event.Ui.ClickTrip -> {
            effects { +Effect.ToTripInformationActivity(event.tripId) }
        }
        is Event.Ui.ShowTripsList -> {
            commands { +Command.LoadAllTrips(event.userId) }
        }
        is Event.Ui.Init -> {}
    }
}

class MyActor : Actor<Command, Event> {

    private val tripRepository: TripRepository = TripRepository(
        NetworkService.instance.getAuthService()
    )

    override fun execute(command: Command): Observable<Event> = when (command) {
        is Command.LoadAllTrips -> tripRepository
            .getAllTrips(command.userId)
            .mapEvents(
                eventMapper = { response ->
                    response.statusCodeHandler(
                        successHandler = {
                            Event.Internal.SuccessLoading(it)
                        },
                        errorHandler = { Event.Internal.ErrorLoading }
                    )
                },
                errorMapper = { Event.Internal.ErrorLoading }
            )
    }
}

fun storeFactory() = ElmStoreCompat(
    initialState = State(),
    reducer = Reducer(),
    actor = MyActor()
)