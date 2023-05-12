package com.example.businesstripsapp.main_activity.profile_fragment.presentation

import com.example.businesstripsapp.main_activity.domain.UserRepository
import com.example.businesstripsapp.main_activity.profile_fragment.presentation.Event.Internal
import com.example.businesstripsapp.main_activity.profile_fragment.presentation.Event.Ui
import com.example.businesstripsapp.network.NetworkService
import com.example.businesstripsapp.network.ext.statusCodeHandler
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
            state { copy(loading = false, user = event.user) }
        }
    }

    override fun Result.ui(event: Ui) = when (event) {
        is Ui.Init -> {
            state { copy(loading = true) }
            commands { +Command.LoadProfile(event.id) }
        }
        is Ui.CLickExit -> {
            effects { +Effect.ExitToStart }
        }
        is Ui.ClickSubordinate -> {
            effects { +Effect.ToSubordinateDetails(event.subordinate) }
        }
    }
}

class MyActor : Actor<Command, Event> {

    private val userRepository: UserRepository = UserRepository(
        NetworkService.instance.getService()
    )

    override fun execute(command: Command): Observable<Event> = when (command) {
        is Command.LoadProfile -> userRepository
            .getUser(command.id)
            .mapEvents(eventMapper = { response ->
                response.statusCodeHandler(
                    successHandler = {
                        Internal.SuccessLoad(user = it)
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