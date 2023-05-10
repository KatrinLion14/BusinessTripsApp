package com.example.businesstripsapp.main_activity.main_fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.businesstripsapp.R
import com.example.businesstripsapp.main_activity.domain.models.Request
import com.example.businesstripsapp.main_activity.domain.models.constant.RequestStatus.APPROVED
import com.example.businesstripsapp.main_activity.domain.models.constant.RequestStatus.AWAIT_CHANGES
import com.example.businesstripsapp.main_activity.domain.models.constant.RequestStatus.DECLINED
import com.example.businesstripsapp.main_activity.domain.models.constant.RequestStatus.PENDING
import com.example.businesstripsapp.main_activity.main_fragment.HomeRequestsRecyclerAdapter.HomeRequestViewHolder

class HomeRequestsRecyclerAdapter(private val dataset: List<Request>, private val view: View) :
    RecyclerView.Adapter<HomeRequestViewHolder>() {

    class HomeRequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val addressTextView: TextView = itemView.findViewById(R.id.address)
        val statusTextView: TextView = itemView.findViewById(R.id.status)
        val dateTextView: TextView = itemView.findViewById(R.id.date)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, ViewType: Int): HomeRequestViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.main_fragment_list_item, parent, false)
        return HomeRequestViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: HomeRequestViewHolder, position: Int) {
        with(viewHolder) {
            addressTextView.text = dataset[position].destination.office.address
            when (dataset[position].requestStatus) {
                APPROVED -> {
                    statusTextView.text = "Утверждена"
                    statusTextView.setTextColor(view.resources.getColor(R.color.positive_500))
                }
                PENDING -> {
                    statusTextView.text = "В обработке"
                    statusTextView.setTextColor(view.resources.getColor(R.color.info_600))
                }
                AWAIT_CHANGES -> {
                    statusTextView.text = "Возвращена"
                    statusTextView.setTextColor(view.resources.getColor(R.color.primary_900))
                }
                DECLINED -> {
                    statusTextView.text = "Отклонена"
                    statusTextView.setTextColor(view.resources.getColor(R.color.negative_500))
                }
            }
            dateTextView.text = "${dataset[position].startDate} - ${dataset[position].endDate}"
        }
    }
}