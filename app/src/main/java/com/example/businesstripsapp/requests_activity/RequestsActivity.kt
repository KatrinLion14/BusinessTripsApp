package com.example.businesstripsapp.requests_activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import com.example.businesstripsapp.R
import com.example.businesstripsapp.requests_activity.requests_fragment.outgoing_requests_fragment.OutgoingRequestsFragment
import com.example.businesstripsapp.requests_activity.presentation.Effect
import com.example.businesstripsapp.requests_activity.presentation.Event
import com.example.businesstripsapp.requests_activity.presentation.State
import com.example.businesstripsapp.requests_activity.presentation.storeFactory
import com.example.businesstripsapp.requests_history_activity.RequestsHistoryActivity
import com.example.businesstripsapp.main_activity.MainActivity
import com.example.businesstripsapp.request_create_activity.RequestCreateActivity
import com.example.businesstripsapp.requests_activity.requests_fragment.RequestsFragment
import org.json.JSONObject
import vivid.money.elmslie.android.base.ElmActivity
import vivid.money.elmslie.core.store.Store
import java.util.Base64

class RequestsActivity : ElmActivity<Event, Effect, State>(R.layout.activity_requests) {

    override val initEvent: Event = Event.Ui.Init //событие инициализации экрана

    //private var requestsFragment: RequestsFragment = RequestsFragment()
    private var outgoingRequestsFragment: OutgoingRequestsFragment = OutgoingRequestsFragment()
    private var requestsFragment: RequestsFragment = RequestsFragment()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_requests)

        val token = intent?.getStringExtra("token") ?: ""
        val userId = getUserId(token)

        supportFragmentManager.beginTransaction().replace(R.id.container, requestsFragment).commit()

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

    override fun createStore(): Store<Event, Effect, State> = storeFactory() //создает Store

    override fun render(state: State) {  //отрисовывает State на экране
        //findViewById<TextView>(R.id.currentValue).text = when {
        //    state.isLoading -> "Loading..."
        //    state.requestsList.isEmpty() -> "Value = Unknown"
        //    else -> "Value = ${state.value}"
        //}
        Log.i("STATE", "render state")
    }

    override fun handleEffect(effect: Effect) = when (effect) {  //обрабатывает side Effect
        is Effect.ToMainActivity -> toMainActivity()
        is Effect.ToRequestsHistoryActivity -> toRequestsHistoryActivity()
        is Effect.ToRequestCreateActivity -> toRequestCreateActivity()
    }

    private fun toMainActivity() {
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun toRequestsHistoryActivity() {
        val intent: Intent = Intent(this, RequestsHistoryActivity::class.java)
        startActivity(intent)
    }

    private fun toRequestCreateActivity() {
        val intent: Intent = Intent(this, RequestCreateActivity::class.java)
        startActivity(intent)
    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun getUserId(token: String) : String {
        val splitString = token.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        val base64EncodedBody = splitString[1]
        val body = String(Base64.getDecoder().decode(base64EncodedBody))
        val jsonObject = JSONObject(body)

        return jsonObject["id"].toString()
    }

}
