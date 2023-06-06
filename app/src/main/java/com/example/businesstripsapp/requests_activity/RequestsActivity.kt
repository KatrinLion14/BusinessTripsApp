package com.example.businesstripsapp.requests_activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import com.auth0.android.jwt.JWT
import com.example.businesstripsapp.R
import com.example.businesstripsapp.requests_activity.requests_fragment.outgoing_requests_fragment.OutgoingRequestsFragment
import com.example.businesstripsapp.requests_activity.presentation.Effect
import com.example.businesstripsapp.requests_activity.presentation.Event
import com.example.businesstripsapp.requests_activity.presentation.State
import com.example.businesstripsapp.requests_activity.presentation.storeFactory
import com.example.businesstripsapp.requests_history_activity.RequestsHistoryActivity
import com.example.businesstripsapp.create_request_activity.CreateRequestActivity
import com.example.businesstripsapp.network.NetworkService
import com.example.businesstripsapp.requests_activity.requests_fragment.RequestsFragment
import org.json.JSONObject
import vivid.money.elmslie.android.base.ElmActivity
import vivid.money.elmslie.core.store.Store
import java.util.Base64

class RequestsActivity : ElmActivity<Event, Effect, State>(R.layout.activity_requests) {

    override val initEvent: Event = Event.Ui.Init //событие инициализации экрана

    override fun createStore(): Store<Event, Effect, State> = storeFactory()

    private val token = NetworkService.instance.getToken()
    private val jwt: JWT = JWT(token)
    private val role: String = jwt.getClaim("role").asString() ?: ""
    private val userId: String = jwt.getClaim("id").asString() ?: ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_requests)

        if (role == "ADMIN") {
            supportFragmentManager.beginTransaction().replace(R.id.container, RequestsFragment()).commit()
        } else {
            supportFragmentManager.beginTransaction().replace(R.id.container, OutgoingRequestsFragment()).commit()
        }

        val buttonBack: ImageButton = findViewById(R.id.button_back)
        buttonBack.setOnClickListener {
            store.accept(Event.Ui.OnBackClicked)
        }

        val buttonHistory : ImageButton = findViewById(R.id.button_history)
        buttonHistory.setOnClickListener {
            store.accept(Event.Ui.OnRequestHistoryClicked)
        }

        val buttonCreateRequest : Button = findViewById(R.id.button_plus)
        buttonCreateRequest.setOnClickListener {
            store.accept(Event.Ui.OnCreateRequestClicked)
        }
    }

    override fun render(state: State) {  //отрисовывает State на экране
        Log.i("STATE", "render state")
    }

    override fun handleEffect(effect: Effect) = when (effect) {  //обрабатывает side Effect
        is Effect.ToPreviousActivity -> finish()
        is Effect.ToRequestsHistoryActivity -> toRequestsHistoryActivity()
        is Effect.ToRequestCreateActivity -> toRequestCreateActivity()
    }

    private fun toRequestsHistoryActivity() {
        val intent: Intent = Intent(this, RequestsHistoryActivity::class.java)
        startActivity(intent)
    }

    private fun toRequestCreateActivity() {
        val intent: Intent = Intent(this, CreateRequestActivity::class.java)
        startActivity(intent)
    }

}
