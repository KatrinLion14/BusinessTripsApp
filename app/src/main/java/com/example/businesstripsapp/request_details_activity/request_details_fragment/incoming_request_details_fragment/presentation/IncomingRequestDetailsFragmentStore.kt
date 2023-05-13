package com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.presentation

import com.example.businesstripsapp.network.NetworkService
import com.example.businesstripsapp.network.ext.statusCodeHandler
import io.reactivex.Observable
import vivid.money.elmslie.core.Actor
import vivid.money.elmslie.core.ElmStoreCompat
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

class Reducer :
    ScreenDslReducer<Event, Event.Ui, Event.Internal, State, Effect, Command>(Event.Ui::class, Event.Internal::class) {
    override fun Result.internal(event: Event.Internal) = when (event) {
        is Event.Internal.SuccessLoading -> {
            state { copy(isLoading = false) }
            effects { +Effect.ShowRequestDetails(event.request) }
        }
        is Event.Internal.ErrorLoading -> {
            state { copy(isLoading = false) }
            effects { +Effect.ShowError }
        }
    }

    override fun Result.ui(event: Event.Ui) = when (event) {
        is Event.Ui.OnTripClicked -> {
            effects { +Effect.ToTripDetailsActivity(event.tripId) }
        }
        is Event.Ui.ShowRequestDetails -> {
            commands { +Command.LoadRequestDetails(event.requestId) }
        }
        is Event.Ui.Init -> {}
        is Event.Ui.OnApproveClicked -> {
            effects { +Effect.ChangeStatusApproved(event.requestId) }
        }
        is Event.Ui.OnDeclineClicked -> {
            effects { +Effect.ChangeStatusDeclined(event.requestId) }
        }
        is Event.Ui.OnReturnClicked -> {
            effects { +Effect.ChangeStatusPending(event.requestId) }
        }
        is Event.Ui.OnTicketClicked -> {
            effects { +Effect.OpenTicket(event.ticket) }
        }
    }

}

class MyActor : Actor<Command, Event> {

    private val requestRepository : IncomingRequestDetailsRepository = IncomingRequestDetailsRepository(
        NetworkService.instance.getAuthService())

    override fun execute(command: Command): Observable<Event> = when (command) {
        is Command.LoadRequestDetails -> requestRepository.getAllRequests(command.requestId)
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