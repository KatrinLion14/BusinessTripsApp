package com.example.businesstripsapp.trips_history_activity.recycler_view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.businesstripsapp.databinding.TripHistoryListItemBinding
import com.example.businesstripsapp.trips_activity.models.Trip

class TripHistoryViewHolder(item: View): RecyclerView.ViewHolder(item) {
    val binding = TripHistoryListItemBinding.bind(item)

    fun bind(trip: Trip, listener: TripsHistoryAdapter.Listener) {
        binding.tripId.text = "№" + trip.id
        binding.destinationCity.text = trip.destinationCity
        binding.tripDate.text = trip.tripDate

        itemView.setOnClickListener {
            listener.onClick(trip)
        }
    }

}