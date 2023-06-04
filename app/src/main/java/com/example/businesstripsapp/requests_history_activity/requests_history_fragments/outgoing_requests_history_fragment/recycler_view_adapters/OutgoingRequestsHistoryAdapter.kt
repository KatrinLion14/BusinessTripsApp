package com.example.businesstripsapp.requests_history_activity.requests_history_fragments.outgoing_requests_history_fragment.recycler_view_adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.businesstripsapp.R
import com.example.businesstripsapp.requests_history_activity.requests_history_fragments.outgoing_requests_history_fragment.domain.models.Request
import java.text.SimpleDateFormat
import java.util.Date

class OutgoingRequestsHistoryAdapter(val requestsArray: Array<Request>, val listener: Listener): RecyclerView.Adapter<OutgoingRequestsHistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OutgoingRequestsHistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_outgoing_request, parent, false)
        return OutgoingRequestsHistoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return requestsArray.size
    }

    override fun onBindViewHolder(holder: OutgoingRequestsHistoryViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val request = requestsArray[position]

        val requestId = "№" + (request.id)
        holder.requestId.text = requestId

        val destinationCity = request.destination.office.address
        holder.destination.text = destinationCity

        val startDate = request.startDate
        holder.startDate.text = DateToString(startDate)

        val endDate = request.endDate
        holder.endDate.text = DateToString(endDate)
    }

    interface Listener {
        fun onClick(request: Request)
    }

    private fun DateToString(date: Date?) : String? {
        val OLD_FORMAT = "yyyy-MM-dd"
        val NEW_FORMAT = "dd.MM.yyyy"
        val format = SimpleDateFormat(OLD_FORMAT)
        if (date != null) {
            val oldDateString : String = format.format(date)
            val newDateString: String
            val d: Date = format.parse(oldDateString)
            format.applyPattern(NEW_FORMAT)
            newDateString = format.format(d)
            return newDateString
        } else {
            return null
        }
    }
}
