package com.example.businesstripsapp.requests_history_activity.requests_history_fragments.incoming_requests_history_fragment.presentation

import com.example.businesstripsapp.network.NetworkService
import com.example.businesstripsapp.network.ext.statusCodeHandler
import com.example.businesstripsapp.requests_history_activity.requests_history_fragments.incoming_requests_history_fragment.domain.IncomingRequestRepository
import io.reactivex.Observable
import vivid.money.elmslie.core.Actor
import vivid.money.elmslie.core.ElmStoreCompat
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

class Reducer :
    ScreenDslReducer<Event, Event.Ui, Event.Internal, State, Effect, Command>(Event.Ui::class, Event.Internal::class) {
    override fun Result.internal(event: Event.Internal) = when (event) {
        is Event.Internal.SuccessLoading -> {
            state { copy(isLoading = false) }
            effects { +Effect.ShowAllRequests(event.requestsArray) }
        }
        is Event.Internal.ErrorLoading -> {
            state { copy(isLoading = false) }
            effects { +Effect.ShowError }
        }
    }

    override fun Result.ui(event: Event.Ui) = when (event) {
        is Event.Ui.OnRequestClicked -> {
            effects { +Effect.ToRequestDetailsActivity(event.requestId) }
        }
        is Event.Ui.ShowRequests -> {
            state { copy(isLoading = true) }
            commands { +Command.LoadRequests(event.userId) }
        }
        is Event.Ui.Init -> {}
    }

}

class MyActor : Actor<Command, Event> {

    private val requestRepository : IncomingRequestRepository = IncomingRequestRepository(
        NetworkService.instance.getAuthService())

    override fun execute(command: Command): Observable<Event> = when (command) {
        is Command.LoadRequests -> requestRepository.getAllRequests(command.userId)
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