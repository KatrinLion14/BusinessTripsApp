package com.example.businesstripsapp.request_details_activity.request_details_fragment.outgoing_request_details_fragment.presentation

import com.example.businesstripsapp.network.NetworkService
import com.example.businesstripsapp.network.ext.statusCodeHandler
import com.example.businesstripsapp.request_details_activity.request_details_fragment.outgoing_request_details_fragment.domain.OutgoingRequestDetailsRepository
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
    }

    override fun Result.ui(event: Event.Ui) = when (event) {
        is Event.Ui.Init -> {}
        is Event.Ui.GetRequestDetails -> {
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

    private val requestRepository : OutgoingRequestDetailsRepository = OutgoingRequestDetailsRepository(
        NetworkService.instance.getAuthService()
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

    }
}

fun storeFactory() = ElmStoreCompat(
    initialState = State(),
    reducer = Reducer(),
    actor = MyActor()
)