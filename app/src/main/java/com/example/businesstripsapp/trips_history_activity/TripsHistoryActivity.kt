package com.example.businesstripsapp.trips_history_activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.businesstripsapp.R
import com.example.businesstripsapp.trip_info_activity.TripInfoActivity
import com.example.businesstripsapp.trips_activity.models.Trip
import com.example.businesstripsapp.trips_history_activity.presentation.Effect
import com.example.businesstripsapp.trips_history_activity.presentation.Event
import com.example.businesstripsapp.trips_history_activity.presentation.State
import com.example.businesstripsapp.trips_history_activity.presentation.storeFactory
import com.example.businesstripsapp.trips_history_activity.recycler_view.TripsHistoryAdapter
import vivid.money.elmslie.android.base.ElmActivity
import vivid.money.elmslie.core.store.Store

class TripsHistoryActivity: ElmActivity<Event, Effect, State>(R.layout.activity_trips), TripsHistoryAdapter.Listener{
    override val initEvent: Event = Event.Ui.Init

    lateinit var adapter: TripsHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trips_history)

        setSupportActionBar(findViewById(R.id.tripsHistoryToolbar))

        val tripsArray = intent.getParcelableArrayExtra("tripsArray")?: arrayOf()

        adapter = TripsHistoryAdapter(arrayOf(Trip("1023325457", "Предстоит", "Москва", "15.03.2023"), Trip("4634636536", "Предстоит", "Пермь", "13.04.2023")), this)

        makeTripList(arrayOf(Trip("1023325457", "Предстоит", "Москва", "15.03.2023"), Trip("4634636536", "Предстоит", "Пермь", "13.04.2023")))
    }

    private fun initRecyclerView() {
        R.layout.activity_trips_history.apply {
            findViewById<RecyclerView>(R.id.tripsHistoryRecyclerView).layoutManager = LinearLayoutManager(this@TripsHistoryActivity)
            findViewById<RecyclerView>(R.id.tripsHistoryRecyclerView).adapter = adapter
        }
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
        is Effect.ToTripInformationActivity -> toTripInfo(effect.tripId)
        is Effect.BackToTripsActivity -> finish()
        is Effect.ShowAllTrips -> initRecyclerView()
    }

    private fun makeTripList(tripsArray: Array<Trip>) {
        store.accept(
            Event.Internal.MakeTripList(tripsArray)
        )
    }

    private fun toTripInfo(tripId: String) {
        val intent = Intent(this, TripInfoActivity::class.java).apply {
            putExtra("tripId", tripId)
        }
        startActivity(intent)
    }

    override fun onClick(trip: Trip) {
        store.accept(
            Event.Ui.ClickTrip(trip.id)
        )
    }
}