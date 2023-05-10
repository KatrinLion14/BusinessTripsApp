package com.example.businesstripsapp.main_activity.main_fragment.presentation

import com.example.businesstripsapp.main_activity.domain.UserRepository
import com.example.businesstripsapp.main_activity.main_fragment.presentation.Event.Internal
import com.example.businesstripsapp.main_activity.main_fragment.presentation.Event.Ui
import com.example.businesstripsapp.network.NetworkService
import com.example.businesstripsapp.network.ext.statusCodeHandler
import io.reactivex.Observable
import vivid.money.elmslie.core.Actor
import vivid.money.elmslie.core.ElmStoreCompat
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

class Reducer :
    ScreenDslReducer<Event, Ui, Internal, State, Effect, Command>(Ui::class, Internal::class) {

    override fun Result.internal(event: Internal) = when (event) {
        is Internal.ErrorNetwork -> {
            state { copy(loading = false) }
            effects { +Effect.ShowErrorNetwork }
        }

        is Internal.SuccessLoadRequests -> {
            state { copy(loading = false, requests = event.requests) }
        }

        is Internal.SuccessLoadTrips -> {
            state { copy(loading = false, trips = event.trips) }
        }

        is Internal.ErrorLoading -> {
            state { copy(loading = false) }
            effects { +Effect.ShowLoadingError }
        }
    }

    override fun Result.ui(event: Ui) = when (event) {
        is Ui.Init -> {
            state { copy(loading = true) }
            commands { listOf(+Command.LoadRequests(event.id), +Command.LoadTrips(event.id)) }
        }

        is Ui.ClickNotificaitons -> {
            effects { +Effect.ToNotificationActivity }
        }

        is Ui.ClickRequests -> {
            effects { +Effect.ToRequestsActivity }
        }

        is Ui.ClickTrips -> {
            effects { +Effect.ToTripsActivity }
        }

        is Ui.PullToRefrech -> {
            state { copy(loading = true) }
            commands { listOf(+Command.LoadRequests(event.id), +Command.LoadTrips(event.id)) }
        }
    }
}

class MyActor : Actor<Command, Event> {

    private val userRepository: UserRepository = UserRepository(
        NetworkService.instance.getService()
    )

    override fun execute(command: Command): Observable<Event> = when (command) {
        is Command.LoadRequests -> userRepository
            .getRequests(command.id)
            .mapEvents(eventMapper = { response ->
                response.statusCodeHandler(
                    successHandler = {
                        Internal.SuccessLoadRequests(requests = it)
                    },
                    errorHandler = { Internal.ErrorLoading }
                )
            },
                errorMapper = { Internal.ErrorNetwork })

        is Command.LoadTrips -> userRepository
            .getTrips(command.id)
            .mapEvents(eventMapper = { response ->
                response.statusCodeHandler(
                    successHandler = {
                        Internal.SuccessLoadTrips(trips = it)
                    },
                    errorHandler = { Internal.ErrorLoading }
                )
            },
                errorMapper = { Internal.ErrorNetwork })
    }
}

fun storeFactory() = ElmStoreCompat(
    initialState = State(),
    reducer = Reducer(),
    actor = MyActor()
)