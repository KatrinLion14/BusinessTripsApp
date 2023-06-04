package com.example.businesstripsapp.request_edit_activity.presentation

import com.example.businesstripsapp.network.NetworkService
import com.example.businesstripsapp.network.ext.statusCodeHandler
import com.example.businesstripsapp.request_edit_activity.domain.EditRequestRepository
import com.example.businesstripsapp.request_edit_activity.presentation.Effect
import com.example.businesstripsapp.request_edit_activity.presentation.Event
import com.example.businesstripsapp.request_edit_activity.presentation.State
import io.reactivex.Observable
import vivid.money.elmslie.core.Actor
import vivid.money.elmslie.core.ElmStoreCompat
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer


// TODO
class Reducer :
    ScreenDslReducer<Event, Event.Ui, Event.Internal, State, Effect, Command>(Event.Ui::class, Event.Internal::class) {

    override fun Result.internal(event: Event.Internal) = when (event) {
        is Event.Internal.SuccessDetailsLoading -> {
            state { copy(isLoading = false) }
            effects { +Effect.ShowRequestDetails(event.request) }
        }
        is Event.Internal.ErrorDetailsLoading -> {
            state { copy(isLoading = false) }
            effects { +Effect.ShowErrorLoading }
        }
        is Event.Internal.SuccessEditRequest -> {
            state { copy(isLoading = false) }
            effects { +Effect.ToRequestDetailsActivity }
        }

        is Event.Internal.ErrorEditRequest -> {
            state { copy(isLoading = false) }
            effects { +Effect.ShowErrorEditRequest }
        }

        is Event.Internal.ErrorNetwork -> {
            state { copy(isLoading = false) }
            effects { +Effect.ShowErrorNetwork }
        }

        is Event.Internal.ErrorEditDestination -> {
            state { copy(isLoading = false) }
            effects { +Effect.ShowErrorEditDestination }
        }

        is Event.Internal.SuccessEditDestination -> {
            state { copy(isLoading = false) }
            effects { +Effect.DestinationEdited }
        }

    }

    override fun Result.ui(event: Event.Ui) = when (event) {
        is Event.Ui.Init -> {}
        is Event.Ui.GetRequestDetails -> {
            state { copy(isLoading = true) }
            commands { +Command.LoadRequestDetails(event.requestId) }
        }
        is Event.Ui.OnSaveClicked -> {
            state { copy(isLoading = true) }
            commands { +Command.EditDestination(event.destinationUpdate) }
        }
        is Event.Ui.OnDestinationEdited -> {
            state { copy(isLoading = true) }
            commands { +Command.EditRequest(event.requestUpdate) }
        }
        is Event.Ui.OnBackClicked -> {
            effects { +Effect.ShowDialog }
        }
        is Event.Ui.OnExitClicked -> {
            effects { +Effect.ToRequestDetailsActivity }
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
    private val requestRepository: EditRequestRepository = EditRequestRepository(
        NetworkService.instance.getService()
    )

    override fun execute(command: Command): Observable<Event> = when (command) {

        is Command.LoadRequestDetails -> requestRepository.getRequest(command.requestId)
            .mapEvents(
                eventMapper = { response ->
                    response.statusCodeHandler(
                        successHandler = {
                            Event.Internal.SuccessDetailsLoading(it)
                        },
                        errorHandler = { Event.Internal.ErrorDetailsLoading }
                    )
                },
                errorMapper = { Event.Internal.ErrorDetailsLoading }
            )

        is Command.EditRequest -> requestRepository
            .editRequest(command.requestUpdate)
            .mapEvents(
                eventMapper = { response ->
                    response.statusCodeHandler(
                        successHandler = { Event.Internal.SuccessEditRequest },
                        errorHandler = { Event.Internal.ErrorEditRequest }
                    )
                },
                errorMapper = { Event.Internal.ErrorNetwork }
            )

        is Command.EditDestination -> requestRepository
            .editDestination(command.destinationUpdate)
            .mapEvents(
                eventMapper = { response ->
                    response.statusCodeHandler(
                        successHandler = { Event.Internal.SuccessEditDestination },
                        errorHandler = { Event.Internal.ErrorEditDestination }
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