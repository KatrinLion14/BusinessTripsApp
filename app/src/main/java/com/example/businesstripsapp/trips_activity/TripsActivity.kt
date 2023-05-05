package com.example.businesstripsapp.trips_activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.businesstripsapp.R
import com.example.businesstripsapp.trip_info_activity.TripInfoActivity
import com.example.businesstripsapp.trips_activity.models.Accommodation
import com.example.businesstripsapp.trips_activity.models.Destination
import com.example.businesstripsapp.trips_activity.models.Office
import com.example.businesstripsapp.trips_activity.models.Trip
import com.example.businesstripsapp.trips_activity.presentation.Effect
import com.example.businesstripsapp.trips_activity.presentation.Event
import com.example.businesstripsapp.trips_activity.presentation.State
import com.example.businesstripsapp.trips_activity.presentation.storeFactory
import com.example.businesstripsapp.trips_activity.recycler_view.TripsAdapter
import com.example.businesstripsapp.trips_history_activity.TripsHistoryActivity
import org.json.JSONObject
import vivid.money.elmslie.android.base.ElmActivity
import vivid.money.elmslie.core.store.Store
import java.util.Base64
import java.util.Date


class TripsActivity: ElmActivity<Event, Effect, State>(R.layout.activity_trips), TripsAdapter.Listener {
    override val initEvent: Event = Event.Ui.Init

    var tripsArray : Array<Trip> = arrayOf(
        Trip("1023325457", "Предстоит", Accommodation("123", "123", "123"), Destination("123", "123", Office("123", "123", "123"), "123"), "123", "123", "123"),
        Trip("4634636536", "Предстоит", Accommodation("123", "123", "123"), Destination("123", "123", Office("123", "123", "123"), "123"), "123", "123", "123"),
        Trip("3524523523", "Предстоит", Accommodation("123", "123", "123"), Destination("123", "123", Office("123", "123", "123"), "123"), "123", "123", "123"),
    )

    private var adapter = TripsAdapter(tripsArray, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trips)

        setSupportActionBar(findViewById(R.id.tripsToolbar))

        val token = intent.getStringExtra("token")?: ""
        val userId = getUserId(token)

        initRecyclerView()

        loadAllTrips(userId)
    }

    private fun getUserId(token: String) : String {

        val splitString = token.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        val base64EncodedBody = splitString[1]

        val body = String(Base64.getDecoder().decode(base64EncodedBody))

        val jsonObject = JSONObject(body)

        return jsonObject["id"].toString()
    }

    private fun initRecyclerView() {
        R.layout.activity_trips.apply {
            findViewById<RecyclerView>(R.id.tripsRecyclerView).layoutManager = LinearLayoutManager(this@TripsActivity)
            findViewById<RecyclerView>(R.id.tripsRecyclerView).adapter = adapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.trips_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                store.accept(
                    Event.Ui.ClickBack
                )
            }
            R.id.history -> {
                store.accept(
                    Event.Ui.ClickHistory(tripsArray)
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
        is Effect.ShowLoadingError -> Toast.makeText(applicationContext, "Loading error occurs", Toast.LENGTH_SHORT).show()
        is Effect.ToTripInformationActivity ->  toTripInfo(effect.tripId)
        is Effect.BackToMainActivity -> finish()
        is Effect.ToTripsHistoryActivity -> toTripsHistoryActivity(effect.tripsArray)
        is Effect.ShowAllTrips -> tripsArray = effect.tripsArray
    }

    private fun toTripsHistoryActivity(tripsArray: Array<Trip>) {
        val intent = Intent(this, TripsHistoryActivity::class.java).apply {
            putExtra("tripsArray", tripsArray)
        }
        startActivity(intent)
    }

    private fun toTripInfo(tripId: String) {
        val intent = Intent(this, TripInfoActivity::class.java).apply {
            putExtra("tripId", tripId)
        }
        startActivity(intent)
    }

    private fun loadAllTrips(userId : String) {
        store.accept(
            Event.Ui.ShowTripsList(userId)
        )
    }

    override fun onClick(trip: Trip) {
        store.accept(
            Event.Ui.ClickTrip(trip.id)
        )
    }
}