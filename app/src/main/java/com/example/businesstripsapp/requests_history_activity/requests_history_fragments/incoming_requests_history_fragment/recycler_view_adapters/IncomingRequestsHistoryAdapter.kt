package com.example.businesstripsapp.requests_history_activity.requests_history_fragments.incoming_requests_history_fragment.recycler_view_adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.businesstripsapp.R
import com.example.businesstripsapp.requests_history_activity.requests_history_fragments.incoming_requests_history_fragment.domain.models.Request
import java.text.SimpleDateFormat
import java.util.Date

class IncomingRequestsHistoryAdapter(val requestsArray: Array<Request>, val listener: Listener): RecyclerView.Adapter<IncomingRequestHistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomingRequestHistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_incoming_requests_history, parent, false)
        return IncomingRequestHistoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return requestsArray.size
    }

    override fun onBindViewHolder(
        holder: IncomingRequestHistoryViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val request = requestsArray[position]

        val requestId = "№" + (request.id)
        holder.requestId.text = requestId

        val employeeName = request.workerFirstName + " " + request.workerSecondName
        holder.workerName.text = employeeName


        val destinationCity = request.destination.office.address
        holder.destinationCity.text = destinationCity

        val start_date = request.startDate
        holder.start_date.text = DateToString(start_date)

        val end_date = request.endDate
        holder.end_date.text = DateToString(end_date)
    }

    interface Listener {
        fun onClick(request: Request)
    }

    private fun DateToString(date: Date?): String? {
        val OLD_FORMAT = "yyyy-MM-dd"
        val NEW_FORMAT = "dd.MM.yyyy"
        val format = SimpleDateFormat(OLD_FORMAT)
        val oldDateString: String? = date?.let { format.format(it) }
        val newDateString: String
        val d: Date = format.parse(oldDateString)
        format.applyPattern(NEW_FORMAT)
        newDateString = format.format(d)
        return newDateString
    }
}