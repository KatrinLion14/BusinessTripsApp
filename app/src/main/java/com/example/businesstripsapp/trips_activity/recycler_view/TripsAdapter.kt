package com.example.businesstripsapp.trips_activity.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.businesstripsapp.R
import com.example.businesstripsapp.trips_activity.models.Trip

class TripsAdapter(val tripsArray: Array<Trip>, val listener: Listener): RecyclerView.Adapter<TripViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trip_list_item, parent, false)
        return TripViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tripsArray.size
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        holder.bind(tripsArray[position], listener)
    }

    interface Listener {
        fun onClick(trip: Trip)
    }

}