package com.example.businesstripsapp.requests_activity.presentation

import com.example.businesstripsapp.start_activity.presentation.Command
import com.example.businesstripsapp.start_activity.presentation.Effect
import com.example.businesstripsapp.start_activity.presentation.Event
import com.example.businesstripsapp.start_activity.presentation.State
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

class Reducer :
    ScreenDslReducer<Event, Event.Ui, Event.Internal, State, Effect, Command>(Event.Ui::class, Event.Internal::class) {
    override fun Result.internal(event: Event.Internal) = when (event) {

    }

    override fun Result.ui(event: Event.Ui) = when (event) {

    }

}