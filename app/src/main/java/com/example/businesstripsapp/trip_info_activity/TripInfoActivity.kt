package com.example.businesstripsapp.trip_info_activity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.example.businesstripsapp.R
import com.example.businesstripsapp.trip_info_activity.presentation.Effect
import com.example.businesstripsapp.trip_info_activity.presentation.Event
import com.example.businesstripsapp.trip_info_activity.presentation.State
import com.example.businesstripsapp.trip_info_activity.presentation.storeFactory
import vivid.money.elmslie.android.base.ElmActivity
import vivid.money.elmslie.core.store.Store

class TripInfoActivity: ElmActivity<Event, Effect, State>(R.layout.activity_trips) {
    override val initEvent: Event = Event.Ui.Init

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_info)

        setSupportActionBar(findViewById(R.id.tripsHistoryToolbar))

        val tripId = intent.getStringExtra("tripId")?: ""

        showTripInfo(tripId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                store.accept(
                    Event.Ui.ClickBack
                )
            }
        }

        return true
    }

    override fun createStore(): Store<Event, Effect, State> = storeFactory()

    override fun render(state: State) {
        Log.i("STATE", "render state")
    }

    override fun handleEffect(effect: Effect) = when (effect) {
        is Effect.ShowLoadingError -> Toast.makeText(applicationContext, "Loading Error", Toast.LENGTH_SHORT).show()
        is Effect.BackToPreviousActivity -> finish()
        is Effect.ShowTripInfo -> findViewById<TextView>(R.id.tripId).text = effect.trip.id
    }

    private fun showTripInfo(tripId: String) {
        store.accept(
            Event.Ui.ShowTripInfo(tripId)
        )
    }
}