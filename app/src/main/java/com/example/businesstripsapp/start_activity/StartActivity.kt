package com.example.businesstripsapp.start_activity

import android.os.Bundle
import com.example.businesstripsapp.start_activity.presentation.Effect
import com.example.businesstripsapp.start_activity.presentation.Event
import com.example.businesstripsapp.R
import com.example.businesstripsapp.start_activity.presentation.State
import vivid.money.elmslie.android.base.ElmActivity

class StartActivity : ElmActivity<Event, Effect, State>(R.layout.activity_start) {
    override val initEvent: Event
        get() = TODO("Not yet implemented")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }

    override fun render(state: State) {
        TODO("Not yet implemented")
    }
}