package com.example.businesstripsapp.trips_history_activity.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.businesstripsapp.R
import com.example.businesstripsapp.trips_activity.models.Trip

class TripsHistoryAdapter(val tripsArray: Array<Trip>, val listener: Listener): RecyclerView.Adapter<TripHistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripHistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trip_history_list_item, parent, false)
        return TripHistoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tripsArray.size
    }

    override fun onBindViewHolder(holder: TripHistoryViewHolder, position: Int) {
        holder.bind(tripsArray[position], listener)
    }



    interface Listener {
        fun onClick(trip: Trip)
    }

}