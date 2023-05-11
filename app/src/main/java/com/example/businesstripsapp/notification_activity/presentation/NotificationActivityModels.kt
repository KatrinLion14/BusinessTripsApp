package com.example.businesstripsapp.notification_activity.presentation

import com.example.businesstripsapp.main_activity.domain.models.Notification
import com.example.businesstripsapp.main_activity.domain.models.Request

data class State(
    val loading: Boolean = false,
    val notifications: List<Notification> = listOf()
)

sealed class Effect {
    object ShowErrorNetwork : Effect()
    object ShowLoadingError : Effect()
    data class ToNotificationDetails(var request: Request)  : Effect()
}

sealed class Event {
    sealed class Ui : Event() {
        data class Init(var id: String) : Ui()
        data class PullToRefresh(var id: String) : Ui()
        class ClickNotification(var request: Request) : Ui()
    }

    sealed class Internal : Event() {
        data class SuccessLoad(var notifications: List<Notification>) : Internal()
        object ErrorNetwork : Internal()
        object ErrorLoading : Internal()
    }
}

sealed class Command {
    class LoadNotifications(var id: String) : Command()
}