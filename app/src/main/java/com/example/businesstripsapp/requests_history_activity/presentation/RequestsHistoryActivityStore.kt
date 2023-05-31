package com.example.businesstripsapp.requests_history_activity.presentation

import com.example.businesstripsapp.requests_history_activity.presentation.Command
import com.example.businesstripsapp.requests_history_activity.presentation.Effect
import com.example.businesstripsapp.requests_history_activity.presentation.Event
import com.example.businesstripsapp.requests_history_activity.presentation.State
import io.reactivex.Observable
import vivid.money.elmslie.core.Actor
import vivid.money.elmslie.core.ElmStoreCompat
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

class Reducer :
    ScreenDslReducer<Event, Event.Ui, Event.Internal, State, Effect, Command>(Event.Ui::class, Event.Internal::class) {

    override fun Result.ui(event: Event.Ui) = when (event) {
        is Event.Ui.OnBackClicked -> {
            effects { +Effect.ToPreviousActivity }
        }
        is Event.Ui.OnRequestHistoryClicked -> {
            effects { +Effect.ToRequestsHistoryActivity }
        }
        is Event.Ui.OnCreateRequestClicked -> {
            effects { +Effect.ToRequestCreateActivity }
        }
        is Event.Ui.Init -> {}
    }

    override fun Result.internal(event: Event.Internal): Any? {
        TODO("No internal events")
    }

}

class MyActor : Actor<Command, Event> {
    override fun execute(command: Command): Observable<Event> {
        TODO("No commands")
    }


}

fun storeFactory() = ElmStoreCompat(
    initialState = State(),
    reducer = Reducer(),
    actor = MyActor()
)