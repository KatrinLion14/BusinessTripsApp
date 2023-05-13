package com.example.businesstripsapp.requests_activity.requests_fragment.outgoing_requests_fragment.recycler_view_adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.businesstripsapp.R
import com.example.businesstripsapp.requests_activity.requests_fragment.outgoing_requests_fragment.models.Request

class OutgoingRequestsAdapter(val requestsArray: Array<Request>, val listener: Listener): RecyclerView.Adapter<OutgoingRequestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OutgoingRequestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_outgoing_request, parent, false)
        return OutgoingRequestViewHolder(view)
    }

    override fun getItemCount(): Int {
        return requestsArray.size
    }

    override fun onBindViewHolder(holder: OutgoingRequestViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val request = requestsArray[position]

        val requestId = "№" + (request.id)
        holder.requestId.text = requestId

        val requestStatus = request.requestStatus
        holder.requestStatus.text = requestStatus

        val destinationCity = request.destination.office.address
        holder.destination.text = destinationCity

        val startDate = request.startDate
        holder.startDate.text = startDate

        val endDate = request.endDate
        holder.endDate.text = endDate
    }

    interface Listener {
        fun onClick(request: Request)
    }
}