package com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.presentation

import com.example.businesstripsapp.network.NetworkService
import com.example.businesstripsapp.network.ext.statusCodeHandler
import com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.domain.IncomingRequestDetailsRepository
import io.reactivex.Observable
import vivid.money.elmslie.core.Actor
import vivid.money.elmslie.core.ElmStoreCompat
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

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
        is Event.Internal.SuccessApproving -> {
            state { copy(isLoading = false) }
            effects { +Effect.AddTripId(event.tripId) }
            effects { +Effect.UpdateStatus(event.requestId) }
        }
        is Event.Internal.ErrorApproving -> {
            state { copy(isLoading = false) }
            effects { +Effect.ShowErrorApproving }
        }
        is Event.Internal.SuccessDeclining -> {
            state { copy(isLoading = false) }
            effects { +Effect.UpdateStatus(event.requestId) }
        }
        is Event.Internal.ErrorDeclining -> {
            state { copy(isLoading = false) }
            effects { +Effect.ShowErrorDeclining }
        }
        is Event.Internal.SuccessSendingBack -> {
            state { copy(isLoading = false) }
            effects { +Effect.UpdateStatus(event.requestId) }
        }
        is Event.Internal.ErrorSendingBack -> {
            state { copy(isLoading = false) }
            effects { +Effect.ShowErrorSendingBack }
        }
    }

    override fun Result.ui(event: Event.Ui) = when (event) {
        is Event.Ui.OnTripClicked -> {
            effects { +Effect.ToTripDetailsActivity(event.tripId) }
        }
        is Event.Ui.ShowRequestDetails -> {
            state { copy(isLoading = true) }
            commands { +Command.LoadRequestDetails(event.requestId) }
        }
        is Event.Ui.Init -> {}
        is Event.Ui.OnApproveClicked -> {
            commands { +Command.ApproveRequest(event.requestId) }
            state { copy(isLoading = true) }
            commands { +Command.LoadRequestDetails(event.requestId) }
        }
        is Event.Ui.OnDeclineClicked -> {
            commands { +Command.DeclineRequest(event.requestId) }
            state { copy(isLoading = true) }
            commands { +Command.LoadRequestDetails(event.requestId) }
        }
        is Event.Ui.OnSendBackClicked -> {
            commands { +Command.SendBackRequest(event.requestId) }
            state { copy(isLoading = true) }
            commands { +Command.LoadRequestDetails(event.requestId) }
        }
        is Event.Ui.OnTicketClicked -> {
            effects { +Effect.OpenTicket(event.ticket) }
        }
        is Event.Ui.OnCalendarClicked -> {
            effects { +Effect.ShowCalendar(event.date) }
        }
        is Event.Ui.OnMapClicked -> {
            effects { +Effect.ShowMap(event.address) }
        }
    }

}

class MyActor : Actor<Command, Event> {

    private val requestRepository : IncomingRequestDetailsRepository = IncomingRequestDetailsRepository(
        NetworkService.instance.getAuthService())

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

        is Command.ApproveRequest -> requestRepository.approveRequest(command.requestId)
            .mapEvents(
                eventMapper = { response ->
                    response.statusCodeHandler(
                        successHandler = {
                            Event.Internal.SuccessApproving(it, command.requestId)
                        },
                        errorHandler = { Event.Internal.ErrorApproving }
                    )
                },
                errorMapper = { Event.Internal.ErrorApproving }
            )

        is Command.SendBackRequest -> requestRepository.sendRequestForEditing(command.requestId)
            .mapEvents(
                eventMapper = { response ->
                    response.statusCodeHandler(
                        successHandler = {
                            Event.Internal.SuccessSendingBack(command.requestId)
                        },
                        errorHandler = { Event.Internal.ErrorSendingBack }
                    )
                },
                errorMapper = { Event.Internal.ErrorSendingBack }
            )

        is Command.DeclineRequest -> requestRepository.declineRequest(command.requestId)
            .mapEvents(
                eventMapper = { response ->
                    response.statusCodeHandler(
                        successHandler = {
                            Event.Internal.SuccessDeclining(command.requestId)
                        },
                        errorHandler = { Event.Internal.ErrorDeclining }
                    )
                },
                errorMapper = { Event.Internal.ErrorDeclining }
            )
    }
}

fun storeFactory() = ElmStoreCompat(
    initialState = State(),
    reducer = Reducer(),
    actor = MyActor()
)