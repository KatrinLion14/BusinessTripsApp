package com.example.businesstripsapp.requests_activity.requests_fragment.outgoing_requests_fragment.recycler_view_adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.businesstripsapp.R


class OutgoingRequestViewHolder(item: View): RecyclerView.ViewHolder(item) {
    val requestId: TextView = itemView.findViewById(R.id.outgoing_request_id)
    val requestStatus: TextView = itemView.findViewById(R.id.outgoing_request_status)
    val destination: TextView = itemView.findViewById(R.id.outgoing_request_destination)
    val startDate: TextView = itemView.findViewById(R.id.outgoing_request_start_date)
    val endDate: TextView = itemView.findViewById(R.id.outgoing_request_end_date)
}