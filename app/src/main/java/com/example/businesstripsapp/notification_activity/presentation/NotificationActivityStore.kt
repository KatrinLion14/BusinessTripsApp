package com.example.businesstripsapp.notification_activity.presentation

import com.example.businesstripsapp.main_activity.domain.UserRepository
import com.example.businesstripsapp.network.NetworkService
import com.example.businesstripsapp.network.ext.statusCodeHandler
import com.example.businesstripsapp.notification_activity.presentation.Event.Internal
import com.example.businesstripsapp.notification_activity.presentation.Event.Ui
import io.reactivex.Observable
import vivid.money.elmslie.core.Actor
import vivid.money.elmslie.core.ElmStoreCompat
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

class Reducer :
    ScreenDslReducer<Event, Ui, Internal, State, Effect, Command>(Ui::class, Internal::class) {

    override fun Result.internal(event: Internal) = when (event) {
        is Internal.ErrorLoading -> {
            state { copy(loading = false) }
            effects { +Effect.ShowLoadingError }
        }

        is Internal.ErrorNetwork -> {
            state { copy(loading = false) }
            effects { +Effect.ShowErrorNetwork }
        }

        is Internal.SuccessLoad -> {
            state { copy(loading = false, notifications = event.notifications) }
        }
    }

    override fun Result.ui(event: Ui) = when (event) {
        is Ui.ClickNotification -> {
            effects { +Effect.ToNotificationDetails(event.request) }
        }

        is Ui.Init -> {
            state { copy(loading = true) }
            commands { +Command.LoadNotifications(event.id) }
        }

        is Ui.PullToRefresh -> {
            state { copy(loading = true) }
            commands { +Command.LoadNotifications(event.id) }
        }
    }
}

class MyActor : Actor<Command, Event> {

    private val userRepository: UserRepository = UserRepository(
        NetworkService.instance.getService()
    )

    override fun execute(command: Command): Observable<Event> = when (command) {
        is Command.LoadNotifications -> userRepository
            .getNotifications(command.id)
            .mapEvents(eventMapper = { response ->
                response.statusCodeHandler(
                    successHandler = {
                        Internal.SuccessLoad(notifications = it)
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