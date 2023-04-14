package com.example.businesstripsapp.start_activity.presentation

import com.example.businesstripsapp.start_activity.presentation.Event.Internal
import com.example.businesstripsapp.start_activity.presentation.Event.Ui
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

class Reducer :
    ScreenDslReducer<Event, Ui, Internal, State, Effect, Command>(Ui::class, Internal::class) {
    override fun Result.internal(event: Internal) = when (event) {
        is Internal.SuccessAuth -> {
            state { copy(auth = true) }
            effects { +Effect.ToManActivity(event.user.id) }
        }
        is Internal.ErrorAuth -> {
            effects { +Effect.ShowAuthError }
        }
    }

    override fun Result.ui(event: Ui) = when (event) {
        is Ui.ClickStart -> {
            commands { +Command.Auth(event.user.email, event.user.password) }
        }
        is Ui.Init -> TODO()
    }

}