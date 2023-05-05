package com.example.businesstripsapp.trips_activity.recycler_view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.businesstripsapp.databinding.TripListItemBinding
import com.example.businesstripsapp.trips_activity.models.Trip

class TripViewHolder(item: View): RecyclerView.ViewHolder(item) {
    val binding = TripListItemBinding.bind(item)

    fun bind(trip: Trip, listener: TripsAdapter.Listener) {
        binding.tripId.text = "№" + trip.id
        binding.tripStatus.text = trip.tripStatus
        binding.destinationCity.text = trip.destination.office.address
        binding.tripDate.text = trip.startDate

        itemView.setOnClickListener {
            listener.onClick(trip)
        }
    }

}