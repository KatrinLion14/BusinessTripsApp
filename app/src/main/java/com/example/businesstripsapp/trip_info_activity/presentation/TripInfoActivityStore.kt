package com.example.businesstripsapp.trip_info_activity.presentation

import com.example.businesstripsapp.network.NetworkService
import com.example.businesstripsapp.network.ext.statusCodeHandler
import com.example.businesstripsapp.trip_info_activity.domain.TripInfoRepository
import io.reactivex.Observable
import vivid.money.elmslie.core.Actor
import vivid.money.elmslie.core.ElmStoreCompat
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

class Reducer :
    ScreenDslReducer<Event, Event.Ui, Event.Internal, State, Effect, Command>(Event.Ui::class, Event.Internal::class) {
    override fun Result.internal(event: Event.Internal) = when (event) {
        is Event.Internal.SuccessLoading -> {
            state { copy(loading = true) }
            effects { +Effect.ShowTripInfo(event.trip) }
        }
        is Event.Internal.ErrorLoading -> {
            effects { +Effect.ShowLoadingError }
        }
    }

    override fun Result.ui(event: Event.Ui) = when (event) {
        is Event.Ui.ClickBack -> {
            effects { +Effect.BackToPreviousActivity }
        }
        is Event.Ui.ShowTripInfo -> {
            commands { +Command.LoadTrip(event.tripId) }
        }
        is Event.Ui.ClickCalendar -> {
            effects { +Effect.ShowCalendar(event.date) }
        }
        is Event.Ui.ClickMap -> {
            effects { +Effect.ShowMap(event.address) }
        }
        is Event.Ui.ClickRequestField -> {
            effects { +Effect.ToRequestInfo(event.requestId) }
        }
        is Event.Ui.MakeToolbarTitle -> {
            effects { +Effect.ShowToolbarTitle(event.tripId) }
        }
        is Event.Ui.Init -> {}
    }
}

class MyActor : Actor<Command, Event> {

    private val tripInfoRepository: TripInfoRepository = TripInfoRepository(
        NetworkService.instance.getAuthService()
    )

    override fun execute(command: Command): Observable<Event> = when (command) {
        is Command.LoadTrip -> tripInfoRepository
            .getTrip(command.tripId)
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