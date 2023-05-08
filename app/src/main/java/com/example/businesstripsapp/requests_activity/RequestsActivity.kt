package com.example.businesstripsapp.requests_activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.businesstripsapp.R
import com.example.businesstripsapp.main_activity.MainActivity
import com.example.businesstripsapp.requests_activity.fragments.OutgoingRequestsFragment
import com.example.businesstripsapp.requests_activity.fragments.RequestsFragment
import com.example.businesstripsapp.requests_activity.presentation.Effect
import com.example.businesstripsapp.requests_activity.presentation.Event
import com.example.businesstripsapp.requests_activity.presentation.State
import com.example.businesstripsapp.requests_activity.presentation.storeFactory
import com.example.businesstripsapp.requests_history_activity.RequestsHistoryActivity
import com.example.businesstripsapp.start_activity.StartActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import vivid.money.elmslie.android.base.ElmActivity
import vivid.money.elmslie.core.store.Store

class RequestsActivity : ElmActivity<Event, Effect, State>(R.layout.activity_requests) {

    override val initEvent: Event = Event.Ui.Init //событие инициализации экрана

    //private var requestsFragment: RequestsFragment = RequestsFragment()
    private var outgoingRequestsFragment: OutgoingRequestsFragment = OutgoingRequestsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_requests)
        supportFragmentManager.beginTransaction().replace(R.id.container, outgoingRequestsFragment).commit()

        val buttonBack: ImageButton = findViewById(R.id.button_back)
        buttonBack.setOnClickListener {
            store.accept(Event.Ui.OnBackClicked)
        }

        val buttonHistory : ImageButton = findViewById(R.id.button_history)
        buttonHistory.setOnClickListener {
            store.accept(Event.Ui.OnRequestHistoryClicked)
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
        is Effect.ShowError -> Toast.makeText(applicationContext, "Can not show the requests", Toast.LENGTH_SHORT).show()
        is Effect.ToMainActivity -> toMainActivity()
        is Effect.ToRequestsHistoryActivity -> toRequestsHistoryActivity()
        is Effect.ToRequestCreateActivity -> toRequestCreateActivity()
        is Effect.ToRequestDetailsActivity -> toRequestDetailsActivity(effect.requestId)
        is Effect.ShowAllRequests ->
    }

    private fun toMainActivity() {
        val intent: Intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun toRequestsHistoryActivity() {
        val intent: Intent = Intent(this, RequestsHistoryActivity::class.java)
        startActivity(intent)
    }

    private fun toRequestCreateActivity() {
        val intent: Intent = Intent(this, RequestsHistoryActivity::class.java)
        startActivity(intent)
    }

    private fun toRequestDetailsActivity(requestId: String) {
        val intent: Intent = Intent(this, RequestsHistoryActivity::class.java)
        intent.putExtra("requestId", requestId)
        startActivity(intent)
    }
}
