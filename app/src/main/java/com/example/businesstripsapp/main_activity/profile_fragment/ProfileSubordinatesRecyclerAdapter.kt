package com.example.businesstripsapp.main_activity.profile_fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.businesstripsapp.R
import com.example.businesstripsapp.main_activity.domain.models.User
import com.example.businesstripsapp.main_activity.profile_fragment.ProfileSubordinatesRecyclerAdapter.UserViewHolder
import com.example.businesstripsapp.main_activity.profile_fragment.presentation.Effect
import com.example.businesstripsapp.main_activity.profile_fragment.presentation.Event
import com.example.businesstripsapp.main_activity.profile_fragment.presentation.State

import vivid.money.elmslie.core.store.Store

class ProfileSubordinatesRecyclerAdapter(
    private val dataset: List<User>,
    private val store: Store<Event, Effect, State>
) : RecyclerView.Adapter<UserViewHolder>() {

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.fullname)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, ViewType: Int): UserViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.profile_fragment_list_item, parent, false)
        return UserViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: UserViewHolder, position: Int) {
        with(dataset[position]) {
            viewHolder.nameTextView.text =
                "${this.firstName} ${this.secondName}"
        }
        viewHolder.itemView.setOnClickListener {
            store.accept(Event.Ui.ClickSubordinate(dataset[position]))
        }
    }
}