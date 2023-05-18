package com.example.businesstripsapp.create_request_activity.presentation

import com.example.businesstripsapp.create_request_activity.domain.CreateDestinationRepository
import com.example.businesstripsapp.create_request_activity.domain.CreateRequestRepository
import com.example.businesstripsapp.network.NetworkService
import com.example.businesstripsapp.network.ext.statusCodeHandler
import io.reactivex.Observable
import vivid.money.elmslie.core.Actor
import vivid.money.elmslie.core.ElmStoreCompat
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

class Reducer :
    ScreenDslReducer<Event, Event.Ui, Event.Internal, State, Effect, Command>(Event.Ui::class, Event.Internal::class) {

    override fun Result.internal(event: Event.Internal) = when (event) {
        is Event.Internal.SuccessCreateRequest -> {
            state { copy(isLoading = false) }
            effects { +Effect.ToRequestsActivity }
        }

        is Event.Internal.ErrorCreateRequest -> {
            state { copy(isLoading = false) }
            effects { +Effect.ShowErrorCreateRequest }
        }

        is Event.Internal.ErrorNetwork -> {
            state { copy(isLoading = false) }
            effects { +Effect.ShowErrorNetwork }
        }

        is Event.Internal.ErrorCreateDestination -> {
            state { copy(isLoading = false) }
            effects { +Effect.ShowErrorCreateDestination }
        }
        is Event.Internal.SuccessCreateDestination -> {
            state { copy(isLoading = false) }
            effects { +Effect.DestinationCreated(event.createDestination) }
        }

    }

    override fun Result.ui(event: Event.Ui) = when (event) {
        is Event.Ui.Init -> {}
        is Event.Ui.OnCreateClicked -> {
            state { copy(isLoading = true) }
            commands { +Command.CreateDestination(event.destination) }
        }
        is Event.Ui.OnDestinationCreated -> {
            state { copy(isLoading = true) }
            commands { +Command.CreateRequest(event.request) }
        }
        is Event.Ui.OnBackClicked -> {
            effects { +Effect.ToRequestsActivity }
        }
        is Event.Ui.OnCalendarStartDateClicked -> {
            effects { +Effect.ShowCalendarStartDate }
        }
        is Event.Ui.OnCalendarEndDateClicked -> {
            effects { +Effect.ShowCalendarEndDate }
        }

    }
}

class MyActor : Actor<Command, Event> {
    private val requestRepository: CreateRequestRepository = CreateRequestRepository(
        NetworkService.instance.getService()
    )

    private val destinationRepository: CreateDestinationRepository = CreateDestinationRepository(
        NetworkService.instance.getService()
    )

    override fun execute(command: Command): Observable<Event> = when (command) {
        is Command.CreateRequest -> requestRepository
            .createRequest(command.request)
            .mapEvents(
                eventMapper = { response ->
                    response.statusCodeHandler(
                        successHandler = { Event.Internal.SuccessCreateRequest },
                        errorHandler = { Event.Internal.ErrorCreateRequest }
                    )
                },
                errorMapper = { Event.Internal.ErrorNetwork }
            )

        is Command.CreateDestination -> destinationRepository
            .createDestination(command.destination)
            .mapEvents(
                eventMapper = { response ->
                    response.statusCodeHandler(
                        successHandler = { Event.Internal.SuccessCreateDestination(it) },
                        errorHandler = { Event.Internal.ErrorCreateDestination }
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