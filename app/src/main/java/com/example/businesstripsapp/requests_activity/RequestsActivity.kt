package com.example.businesstripsapp.requests_activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.example.businesstripsapp.R
import com.example.businesstripsapp.requests_activity.fragments.RequestsFragment
import com.example.businesstripsapp.start_activity.presentation.Effect
import com.example.businesstripsapp.start_activity.presentation.Event
import com.example.businesstripsapp.start_activity.presentation.State
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import vivid.money.elmslie.android.base.ElmActivity

class RequestsActivity : ElmActivity<Event, Effect, State>(R.layout.activity_requests) {

    private var requestsFragment: RequestsFragment = RequestsFragment()

    override val initEvent: Event
        get() = TODO("Not yet implemented")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_requests)


        val buttonBack: ImageButton = findViewById(R.id.button_back)
        buttonBack.setOnClickListener {
            TODO("Not yet implemented")
        }

        val buttonHistory : ImageButton = findViewById(R.id.button_history)
        buttonHistory.setOnClickListener {
            TODO("Not yet implemented")
        }
    }

    override fun render(state: State) {
        TODO("Not yet implemented")
    }

}