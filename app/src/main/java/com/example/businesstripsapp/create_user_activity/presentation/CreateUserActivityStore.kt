package com.example.businesstripsapp.create_user_activity.presentation

import com.example.businesstripsapp.create_user_activity.presentation.Event.Internal
import com.example.businesstripsapp.create_user_activity.presentation.Event.Ui
import com.example.businesstripsapp.main_activity.domain.UserRepository
import com.example.businesstripsapp.network.NetworkService
import com.example.businesstripsapp.network.ext.statusCodeHandler
import io.reactivex.Observable
import vivid.money.elmslie.core.Actor
import vivid.money.elmslie.core.ElmStoreCompat
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

class Reducer :
    ScreenDslReducer<Event, Ui, Internal, State, Effect, Command>(Ui::class, Internal::class) {

    override fun Result.internal(event: Internal) = when (event) {
        is Internal.SuccessCreateUser -> {
            state { copy(loading = false) }
            effects { +Effect.ReturnToHome }
        }

        is Internal.ErrorCreateUser -> {
            state { copy(loading = false) }
            effects { +Effect.ShowErrorCreateUser }
        }

        is Internal.ErrorNetwork -> {
            state { copy(loading = false) }
            effects { +Effect.ShowErrorNetwork }
        }

        is Internal.ErrorGetSubordinates -> {
            state { copy(loading = false) }
            effects { +Effect.ShowErrorGetSubordinates }
            commands { +Command.CreateUser(event.user) }
        }

        is Internal.SuccessGetSubordinates -> {
            state { copy(loading = true) }
            commands { +Command.CreateUser(event.user) }
        }
    }

    override fun Result.ui(event: Ui) = when (event) {
        is Ui.Init -> {}
        is Ui.CreateClick -> {
            commands { +Command.GetSubordinates(event.user, event.id) }
        }
    }
}

class MyActor : Actor<Command, Event> {
    private val userRepository: UserRepository = UserRepository(
        NetworkService.instance.getService()
    )

    override fun execute(command: Command): Observable<Event> = when (command) {
        is Command.CreateUser -> userRepository
            .createUser(command.user)
            .mapEvents(
                eventMapper = { response ->
                    response.statusCodeHandler(
                        successHandler = { Internal.SuccessCreateUser },
                        errorHandler = { Internal.ErrorCreateUser }
                    )
                },
                errorMapper = { Internal.ErrorNetwork }
            )

        is Command.GetSubordinates -> userRepository
            .getUser(command.id)
            .mapEvents(
                eventMapper = { response ->
                    response.statusCodeHandler(
                        successHandler = { Internal.SuccessGetSubordinates(command.user.copy(
                            subordinates = listOf(it)
                        )) },
                        errorHandler = { Internal.ErrorGetSubordinates(command.user) }
                    )
                },
                errorMapper = { Internal.ErrorNetwork }
            )
    }
}

fun storeFactory() = ElmStoreCompat(
    initialState = State(),
    reducer = Reducer(),
    actor = MyActor()
)