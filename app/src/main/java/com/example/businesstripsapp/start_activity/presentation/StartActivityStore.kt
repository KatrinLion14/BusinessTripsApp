package com.example.businesstripsapp.start_activity.presentation

import com.example.businesstripsapp.network.NetworkService
import com.example.businesstripsapp.network.ext.statusCodeHandler
import com.example.businesstripsapp.start_activity.domain.LoginRepository
import com.example.businesstripsapp.start_activity.presentation.Event.Internal
import com.example.businesstripsapp.start_activity.presentation.Event.Ui
import io.reactivex.Observable
import vivid.money.elmslie.core.Actor
import vivid.money.elmslie.core.ElmStoreCompat
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

class Reducer :
    ScreenDslReducer<Event, Ui, Internal, State, Effect, Command>(Ui::class, Internal::class) {
    override fun Result.internal(event: Internal) = when (event) {
        is Internal.SuccessAuth -> {
            state { copy(auth = true) }
            effects { +Effect.ToMainActivity(event.token) }
        }
        is Internal.ErrorAuth -> {
            effects { +Effect.ShowAuthError }
        }
    }

    override fun Result.ui(event: Ui) = when (event) {
        is Ui.ClickContinue -> {
            commands { +Command.Auth(event.email, event.password) }
        }
        is Ui.Init -> {}
    }
}

class MyActor : Actor<Command, Event> {

    private val loginRepository: LoginRepository = LoginRepository(
        NetworkService().retrofit
    )

    override fun execute(command: Command): Observable<Event> = when (command) {
        is Command.Auth -> loginRepository
            .loginUser(command.email, command.password)
            .mapEvents(
                eventMapper = { response ->
                    response.statusCodeHandler(
                        successHandler = {
                            Internal.SuccessAuth(it)
                        },
                        errorHandler = { Internal.ErrorAuth }
                    )
                },
                errorMapper = { Internal.ErrorAuth }
            )
    }
}

fun storeFactory() = ElmStoreCompat(
    initialState = State(),
    reducer = Reducer(),
    actor = MyActor()
)