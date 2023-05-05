package com.example.businesstripsapp.trips_history_activity.presentation

import io.reactivex.Observable
import com.example.businesstripsapp.network.NetworkService
import com.example.businesstripsapp.network.ext.statusCodeHandler
import com.example.businesstripsapp.trips_history_activity.domain.TripHistoryRepository
import vivid.money.elmslie.core.Actor
import vivid.money.elmslie.core.ElmStoreCompat
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer


class Reducer :
    ScreenDslReducer<Event, Event.Ui, Event.Internal, State, Effect, Command>(Event.Ui::class, Event.Internal::class) {
    override fun Result.internal(event: Event.Internal) = when (event) {
        is Event.Internal.MakeTripList -> {
            state { copy(loading = true) }
            effects { +Effect.ShowAllTrips(event.tripsArray) }
        }
        is Event.Internal.ErrorLoading -> {
            effects { +Effect.ShowLoadingError }
        }
    }

    override fun Result.ui(event: Event.Ui) = when (event) {
        is Event.Ui.ClickBack -> {
            effects { +Effect.BackToTripsActivity }
        }
        is Event.Ui.ClickTrip -> {
            effects { +Effect.ToTripInformationActivity(event.tripId) }
        }
        is Event.Ui.ShowTripList -> {
            commands {  }
        }
        is Event.Ui.Init -> {}
    }
}

class MyActor :
    Actor<Command, Event> {

    private val tripHistoryRepository: TripHistoryRepository = TripHistoryRepository(
        NetworkService.instance.getAuthService()
    )

    override fun execute(command: Command): Observable<Event> = when (command) {
        is Command.LoadAllTrips -> tripHistoryRepository
            .getAllTrips(command.userId)
            .mapEvents(
                eventMapper = { response ->
                    response.statusCodeHandler(
                        successHandler = {
                            Event.Internal.MakeTripList(it)
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