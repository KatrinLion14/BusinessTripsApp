package com.example.businesstripsapp.requests_activity.recycler_view_adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.businesstripsapp.R

class OutgoingRequestViewHolder(item: View): RecyclerView.ViewHolder(item) {
    val requestId: TextView = itemView.findViewById(R.id.outgoing_request_id)
    val requestStatus: TextView = itemView.findViewById(R.id.outgoing_request_status)
    val destinationCity: TextView = itemView.findViewById(R.id.outgoing_request_destination)
    val date: TextView = itemView.findViewById(R.id.outgoing_request_date)
}