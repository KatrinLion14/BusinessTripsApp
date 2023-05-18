package com.example.businesstripsapp.create_accommodation_activity.presentation

import com.example.businesstripsapp.create_accommodation_activity.domain.AccommodationRepository
import com.example.businesstripsapp.network.NetworkService
import com.example.businesstripsapp.network.ext.statusCodeHandler
import io.reactivex.Observable
import vivid.money.elmslie.core.Actor
import vivid.money.elmslie.core.ElmStoreCompat
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

class Reducer :
    ScreenDslReducer<Event, Event.Ui, Event.Internal, State, Effect, Command>(Event.Ui::class, Event.Internal::class) {

    override fun Result.internal(event: Event.Internal) = when (event) {
        is Event.Internal.SuccessCreateAccommodation -> {
            state { copy(isLoading = false) }
            effects { +Effect.ToPreviousActivity(event.createAccommodation) }
        }

        is Event.Internal.ErrorCreateAccommodation -> {
            state { copy(isLoading = false) }
            effects { +Effect.ShowErrorCreateAccommodation }
        }

        is Event.Internal.ErrorNetwork -> {
            state { copy(isLoading = false) }
            effects { +Effect.ShowErrorNetwork }
        }
    }

    override fun Result.ui(event: Event.Ui) = when (event) {
        is Event.Ui.Init -> {}
        is Event.Ui.CreateClick -> {
            commands { +Command.CreateAccommodation(event.accommodation) }
        }
    }
}

class MyActor : Actor<Command, Event> {
    private val accommodationRepository: AccommodationRepository = AccommodationRepository(
        NetworkService.instance.getService()
    )

    override fun execute(command: Command): Observable<Event> = when (command) {
        is Command.CreateAccommodation -> accommodationRepository
            .createAccommodation(command.accommodation)
            .mapEvents(
                eventMapper = { response ->
                    response.statusCodeHandler(
                        successHandler = { Event.Internal.SuccessCreateAccommodation(it) },
                        errorHandler = { Event.Internal.ErrorCreateAccommodation }
                    )
                },
                errorMapper = { Event.Internal.ErrorNetwork }
            )
    }
}

fun storeFactory() = ElmStoreCompat(
    initialState = State(),
    reducer = Reducer(),
    actor = MyActor()
)