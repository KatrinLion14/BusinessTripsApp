package com.example.businesstripsapp.requests_history_activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import com.auth0.android.jwt.JWT
import com.example.businesstripsapp.R
import com.example.businesstripsapp.network.NetworkService
import com.example.businesstripsapp.requests_history_activity.presentation.Effect
import com.example.businesstripsapp.requests_history_activity.presentation.Event
import com.example.businesstripsapp.requests_history_activity.presentation.State
import com.example.businesstripsapp.requests_history_activity.presentation.storeFactory
import com.example.businesstripsapp.requests_history_activity.requests_history_fragments.RequestsHistoryFragment
import com.example.businesstripsapp.requests_history_activity.requests_history_fragments.outgoing_requests_history_fragment.OutgoingRequestsHistoryFragment
import vivid.money.elmslie.android.base.ElmActivity
import vivid.money.elmslie.core.store.Store

class RequestsHistoryActivity : ElmActivity<Event, Effect, State>(R.layout.activity_requests_history) {
    override val initEvent: Event = Event.Ui.Init //событие инициализации экрана

    override fun createStore(): Store<Event, Effect, State> = storeFactory()

    private val token = NetworkService.instance.getToken()
    private val jwt: JWT = JWT(token)
    private val role: String = jwt.getClaim("role").asString() ?: ""
    val id: String = jwt.getClaim("id").asString() ?: ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_requests_history)

        if (role == "ADMIN") {
            supportFragmentManager.beginTransaction().replace(R.id.container, RequestsHistoryFragment()).commit()
        } else {
            supportFragmentManager.beginTransaction().replace(R.id.container, OutgoingRequestsHistoryFragment()).commit()
        }

        val buttonBack: ImageButton = findViewById(R.id.button_back)
        buttonBack.setOnClickListener {
            store.accept(Event.Ui.OnBackClicked)
        }
    }

    override fun render(state: State) {  //отрисовывает State на экране
        Log.i("STATE", "render state")
    }

    override fun handleEffect(effect: Effect) = when (effect) {  //обрабатывает side Effect
        is Effect.ToPreviousActivity -> finish()
    }

}