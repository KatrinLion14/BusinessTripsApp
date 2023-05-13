package com.example.businesstripsapp.requests_activity.requests_fragment.incoming_requests_fragment.recycler_view_adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.businesstripsapp.R

class IncomingRequestViewHolder(item: View): RecyclerView.ViewHolder(item) {
    val requestId: TextView = itemView.findViewById(R.id.incoming_request_id)
    val workerName: TextView = itemView.findViewById(R.id.worker_name)
    val requestStatus: TextView = itemView.findViewById(R.id.incoming_request_status)
    val destinationCity: TextView = itemView.findViewById(R.id.incoming_destination)
    val start_date: TextView = itemView.findViewById(R.id.incoming_start_date)
    val end_date: TextView = itemView.findViewById(R.id.incoming_end_date)
}