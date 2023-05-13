package com.example.businesstripsapp.requests_activity.requests_fragment.incoming_requests_fragment.recycler_view_adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.businesstripsapp.R
import com.example.businesstripsapp.requests_activity.requests_fragment.incoming_requests_fragment.models.Request

class IncomingRequestsAdapter(val requestsArray: Array<Request>, val listener: Listener): RecyclerView.Adapter<IncomingRequestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomingRequestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_outgoing_request, parent, false)
        return IncomingRequestViewHolder(view)
    }

    override fun getItemCount(): Int {
        return requestsArray.size
    }

    override fun onBindViewHolder(holder: IncomingRequestViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val request = requestsArray[position]

        val requestId = "№" + (request.id)
        holder.requestId.text = requestId

        val employeeName = request.workerFirstName + " " + request.workerSecondName
        holder.workerName.text = employeeName

        val requestStatus = request.requestStatus
        holder.requestStatus.text = requestStatus

        val destinationCity = request.destination.office.address
        holder.destinationCity.text = destinationCity

        val start_date = request.startDate
        holder.start_date.text = start_date

        val end_date = request.endDate
        holder.end_date.text = end_date
    }

    interface Listener {
        fun onClick(request: Request)
    }
}