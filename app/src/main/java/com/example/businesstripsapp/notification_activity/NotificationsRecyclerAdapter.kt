package com.example.businesstripsapp.notification_activity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.businesstripsapp.R
import com.example.businesstripsapp.main_activity.domain.models.Notification
import com.example.businesstripsapp.notification_activity.NotificationsRecyclerAdapter.NotificationViewHolder
import com.example.businesstripsapp.notification_activity.presentation.Effect
import com.example.businesstripsapp.notification_activity.presentation.Event
import com.example.businesstripsapp.notification_activity.presentation.State
import vivid.money.elmslie.core.store.Store

class NotificationsRecyclerAdapter(
    private val dataset: List<Notification>,
    private val store: Store<Event, Effect, State>
) :
    RecyclerView.Adapter<NotificationViewHolder>() {

    class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val infoTextView: TextView = itemView.findViewById(R.id.infoNotification)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, ViewType: Int): NotificationViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.notification_activity_list_item, parent, false)
        return NotificationViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: NotificationViewHolder, position: Int) {
        with(dataset[position].request) {
            viewHolder.infoTextView.text =
                "${this?.startDate}, ${this?.destination?.office?.address}, ${this?.workerFirstName}"
        }
        viewHolder.itemView.setOnClickListener {
            store.accept(Event.Ui.ClickNotification(dataset[position].request!!))
        }
    }
}