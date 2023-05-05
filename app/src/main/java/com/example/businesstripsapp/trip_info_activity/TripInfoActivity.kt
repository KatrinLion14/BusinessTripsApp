package com.example.businesstripsapp.trip_info_activity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import com.example.businesstripsapp.R
import com.example.businesstripsapp.trip_info_activity.models.Trip
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
        setSupportActionBar(findViewById(R.id.tripInfoToolbar))

        val tripId = intent.getStringExtra("tripId")?: ""

<<<<<<< HEAD
        store.accept(Event.Ui.MakeToolbarTitle(tripId))
        store.accept(Event.Ui.ShowTripInfo(tripId))


        findViewById<LinearLayout>(R.id.requestLayout).setOnClickListener {
            store.accept(
                Event.Ui.ClickRequestField(findViewById<TextView>(R.id.tripRequest).text.toString())
            )
        }


        findViewById<ImageButton>(R.id.startDateCalendar).setOnClickListener {
            store.accept(
                Event.Ui.ClickCalendar(findViewById<TextView>(R.id.tripStartDate).text.toString())
            )
        }

        findViewById<ImageButton>(R.id.endDateCalendar).setOnClickListener {
            store.accept(
                Event.Ui.ClickCalendar(findViewById<TextView>(R.id.tripEndDate).text.toString())
            )
        }

        findViewById<ImageButton>(R.id.checkInCalendar).setOnClickListener {
            store.accept(
                Event.Ui.ClickCalendar(findViewById<TextView>(R.id.tripCheckInDate).text.toString())
            )
        }

        findViewById<ImageButton>(R.id.checkOutCalendar).setOnClickListener {
            store.accept(
                Event.Ui.ClickCalendar(findViewById<TextView>(R.id.tripCheckOutDate).text.toString())
            )
        }


        findViewById<ImageButton>(R.id.addressMap).setOnClickListener {
            store.accept(
                Event.Ui.ClickMap(findViewById<TextView>(R.id.tripAddress).text.toString())
            )
        }

        findViewById<ImageButton>(R.id.accommodationMap).setOnClickListener {
            store.accept(
                Event.Ui.ClickMap(findViewById<TextView>(R.id.tripAccommodation).text.toString())
            )
        }
=======
        LoadTripInfo(tripId)
>>>>>>> 7210428 (create trip_info layout)
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
        is Effect.ShowTripInfo -> ShowTripInfo(effect.trip)
<<<<<<< HEAD
        is Effect.ShowToolbarTitle -> AddTripIdToToolbarTitle(effect.tripId)

        is Effect.ShowCalendar -> {}
        is Effect.ShowMap -> {}
        is Effect.ToRequestInfo -> {}
    }

    private fun ShowTripInfo(trip : Trip) {
        findViewById<TextView>(R.id.tripNumber).text = trip.id
        findViewById<TextView>(R.id.tripStatus).text = trip.tripStatus
        findViewById<TextView>(R.id.tripDescription).text = trip.destination.description
        findViewById<TextView>(R.id.tripRequest).text = trip.requestId
=======
    }

    private fun ShowTripInfo(trip : Trip) {
        findViewById<TextView>(R.id.tripNumber).text = "№" + trip.id
        findViewById<TextView>(R.id.tripStatus).text = trip.tripStatus
        findViewById<TextView>(R.id.tripDescription).text = trip.destination.description
        findViewById<TextView>(R.id.tripRequest).text = "№" +  trip.requestId
>>>>>>> 7210428 (create trip_info layout)
        findViewById<TextView>(R.id.tripStartDate).text = trip.startDate
        findViewById<TextView>(R.id.tripEndDate).text = trip.endDate
        findViewById<TextView>(R.id.tripAddress).text = trip.destination.office.address
        findViewById<TextView>(R.id.tripTickets).text = trip.accommodation.bookingUrl
        findViewById<TextView>(R.id.tripAccommodation).text = trip.accommodation.address
        findViewById<TextView>(R.id.tripCheckInDate).text = trip.startDate
        findViewById<TextView>(R.id.tripCheckOutDate).text = trip.endDate
    }

<<<<<<< HEAD
    private fun AddTripIdToToolbarTitle(tripId: String) {
        val toolbar = supportActionBar
        toolbar!!.title = toolbar.title.toString() + tripId
=======
    private fun LoadTripInfo(tripId: String) {
        store.accept(
            Event.Ui.ShowTripInfo(tripId)
        )
>>>>>>> 7210428 (create trip_info layout)
    }
}