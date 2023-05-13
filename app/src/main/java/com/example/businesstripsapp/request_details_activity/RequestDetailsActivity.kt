package com.example.businesstripsapp.request_details_activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import com.example.businesstripsapp.R
import com.example.businesstripsapp.request_details_activity.presentation.Effect
import com.example.businesstripsapp.request_details_activity.presentation.Event
import com.example.businesstripsapp.request_details_activity.presentation.State
import com.example.businesstripsapp.request_details_activity.presentation.storeFactory
import com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.IncomingRequestDetailsFragment
import com.example.businesstripsapp.request_details_activity.request_details_fragment.outgoing_request_details_fragment.OutgoingRequestDetailsFragment
import com.example.businesstripsapp.request_edit_activity.RequestEditActivity
import com.example.businesstripsapp.requests_activity.RequestsActivity
import vivid.money.elmslie.android.base.ElmActivity
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.core.store.Store

class RequestDetailsActivity : ElmActivity<Event, Effect, State>(R.layout.activity_request_details) {

    override val initEvent: Event = Event.Ui.Init //событие инициализации экрана

    private var outgoingRequestDetailsFragment: OutgoingRequestDetailsFragment = OutgoingRequestDetailsFragment()
    private var incomingRequestDetailsFragment: IncomingRequestDetailsFragment = IncomingRequestDetailsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_details)

        val requestId : String = intent.getStringExtra("requestId")!!
        val requestType : String? = intent.getStringExtra("requestType")
        if (requestType == "incoming") {
            supportFragmentManager.beginTransaction().replace(R.id.request_details_container, incomingRequestDetailsFragment).commit()
        } else {
            supportFragmentManager.beginTransaction().replace(R.id.request_details_container, outgoingRequestDetailsFragment).commit()
        }

        val buttonBack: ImageButton = findViewById(R.id.button_back)
        buttonBack.setOnClickListener {
            store.accept(Event.Ui.OnBackClicked)
        }

        val buttonEdit : ImageButton = findViewById(R.id.button_edit)
        buttonEdit.setOnClickListener {
            store.accept(Event.Ui.OnEditClicked(requestId))
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
        is Effect.ToRequestsActivity -> toRequestsActivity()
        is Effect.ToRequestsEditActivity -> toRequestsEditActivity(effect.requestId)
    }

    private fun toRequestsActivity() {
        val intent: Intent = Intent(this, RequestsActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun toRequestsEditActivity(requestId : String) {
        val intent: Intent = Intent(this, RequestEditActivity::class.java)
        intent.putExtra("requestId", requestId)
        startActivity(intent)
    }


}
