package com.example.businesstripsapp.create_office_activity.presentation

import com.example.businesstripsapp.create_office_activity.domain.OfficeRepository
import com.example.businesstripsapp.create_office_activity.presentation.Event.*
import com.example.businesstripsapp.network.NetworkService
import com.example.businesstripsapp.network.ext.statusCodeHandler
import io.reactivex.Observable
import vivid.money.elmslie.core.Actor
import vivid.money.elmslie.core.ElmStoreCompat
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

class Reducer :
    ScreenDslReducer<Event, Ui, Internal, State, Effect, Command>(Ui::class, Internal::class) {

    override fun Result.internal(event: Internal) = when (event) {
        is Internal.SuccessCreateOffice -> {
            state { copy(loading = false) }
            effects { +Effect.ReturnToHome }
        }

        is Internal.ErrorCreateOffice -> {
            state { copy(loading = false) }
            effects { +Effect.ShowErrorCreateOffice }
        }

        is Internal.ErrorNetwork -> {
            state { copy(loading = false) }
            effects { +Effect.ShowErrorNetwork }
        }
    }

    override fun Result.ui(event: Ui) = when (event) {
        is Ui.Init -> {}
        is Ui.CreateClick -> {
            commands { +Command.CreateOffice(event.office) }
        }
    }
}

class MyActor : Actor<Command, Event> {
    private val officeRepository: OfficeRepository = OfficeRepository(
        NetworkService.instance.getService()
    )

    override fun execute(command: Command): Observable<Event> = when (command) {
        is Command.CreateOffice -> officeRepository
            .createOffice(command.office)
            .mapEvents(
                eventMapper = { response ->
                    response.statusCodeHandler(
                        successHandler = { Internal.SuccessCreateOffice },
                        errorHandler = { Internal.ErrorCreateOffice }
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