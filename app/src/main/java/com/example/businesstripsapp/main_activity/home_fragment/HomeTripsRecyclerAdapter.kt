package com.example.businesstripsapp.main_activity.home_fragment

import android.annotation.SuppressLint
import com.example.businesstripsapp.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.businesstripsapp.main_activity.domain.models.Trip
import com.example.businesstripsapp.main_activity.domain.models.constant.TripStatus.CANCELLED
import com.example.businesstripsapp.main_activity.domain.models.constant.TripStatus.COMPLETED
import com.example.businesstripsapp.main_activity.domain.models.constant.TripStatus.PENDING
import com.example.businesstripsapp.main_activity.home_fragment.HomeTripsRecyclerAdapter.HomeTripViewHolder

class HomeTripsRecyclerAdapter(private val dataset: List<Trip>, private val view: View) :
    RecyclerView.Adapter<HomeTripViewHolder>() {

    class HomeTripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val addressTextView: TextView = itemView.findViewById(R.id.address)
        val statusTextView: TextView = itemView.findViewById(R.id.status)
        val dateTextView: TextView = itemView.findViewById(R.id.date)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, ViewType: Int): HomeTripViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.main_fragment_list_item, parent, false)
        return HomeTripViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: HomeTripViewHolder, position: Int) {
        with(viewHolder) {
            addressTextView.text = dataset[position].destination.office.address
            when (dataset[position].requestStatus) {
                PENDING -> {
                    statusTextView.text = "Предстоит"
                    statusTextView.setTextColor(view.resources.getColor(R.color.info_600))
                }
                COMPLETED -> {
                    statusTextView.text = "Завершена"
                    statusTextView.setTextColor(view.resources.getColor(R.color.base_100))
                }
                CANCELLED -> {
                    statusTextView.text = "Отменена"
                    statusTextView.setTextColor(view.resources.getColor(R.color.negative_500))
                }
            }
            dateTextView.text = "${dataset[position].startDate} - ${dataset[position].endDate}"
        }
    }
}