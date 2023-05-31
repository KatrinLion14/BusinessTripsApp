package com.example.businesstripsapp.trips_activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.auth0.android.jwt.JWT
import com.example.businesstripsapp.R
import com.example.businesstripsapp.network.NetworkService
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

    lateinit var adapter : TripsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trips)
        setSupportActionBar(findViewById(R.id.tripsToolbar))

        val token = NetworkService.instance.getToken()
        val jwt: JWT = JWT(token)
        val userId: String = jwt.getClaim("id").asString() ?: ""

        store.accept(Event.Ui.ShowTripsList(userId))
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
                    Event.Ui.ClickHistory
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
        is Effect.ToTripsHistoryActivity -> toTripsHistoryActivity()
        is Effect.ShowAllTrips -> showAllTrips(effect.tripsArray)
    }

    private fun toTripsHistoryActivity() {
        val intent = Intent(this, TripsHistoryActivity::class.java)
        startActivity(intent)
    }

    private fun toTripInfo(tripId: String) {
        val intent = Intent(this, TripInfoActivity::class.java).apply {
            putExtra("tripId", tripId)
        }
        startActivity(intent)
    }

    private fun showAllTrips(tripsArray : Array<Trip>) {
//        val tripArray : Array<Trip> = arrayOf(Trip("1023325457", "Предстоит", Accommodation("123", "Москва", "123"), Destination("123", "123", Office("123", "г. Пермь", "123"), "123"), "123", "15.05.2023", "25.05.2023"),
//            Trip("762114579336", "Предстоит", Accommodation("123", "Москва", "123"), Destination("123", "123", Office("123", "г. Москва", "123"), "123"), "123", "14.07.2023", "02.08.2023"))

        val activeTripsArray : Array<Trip> = arrayOf()
        sortTrips(tripsArray, activeTripsArray)

        adapter = TripsAdapter(activeTripsArray, this)

        initRecyclerView()
    }

    private fun sortTrips(tripsArray: Array<Trip>, activeTripsArray: Array<Trip>) {
        var index = 0;
        for (trip in tripsArray) {
            if (trip.tripStatus == "pending") {
                activeTripsArray[index] = trip
                index++
            }
        }
    }

    override fun onClick(trip: Trip) {
        store.accept(
            Event.Ui.ClickTrip(trip.id)
        )
    }
}