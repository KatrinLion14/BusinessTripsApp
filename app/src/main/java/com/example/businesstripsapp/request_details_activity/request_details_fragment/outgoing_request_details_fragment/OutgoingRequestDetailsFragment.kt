package com.example.businesstripsapp.request_details_activity.request_details_fragment.outgoing_request_details_fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.businesstripsapp.R
import com.example.businesstripsapp.request_details_activity.request_details_fragment.outgoing_request_details_fragment.domain.models.Request
import com.example.businesstripsapp.request_details_activity.request_details_fragment.outgoing_request_details_fragment.presentation.Effect
import com.example.businesstripsapp.request_details_activity.request_details_fragment.outgoing_request_details_fragment.presentation.Event
import com.example.businesstripsapp.request_details_activity.request_details_fragment.outgoing_request_details_fragment.presentation.State
import com.example.businesstripsapp.request_details_activity.request_details_fragment.outgoing_request_details_fragment.presentation.storeFactory
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.core.store.Store
import java.text.SimpleDateFormat
import java.util.Date

class OutgoingRequestDetailsFragment : ElmFragment<Event, Effect, State>(R.layout.fragment_outgoing_request_details) {

    override val initEvent: Event = Event.Ui.Init //событие инициализации экрана
    override fun createStore(): Store<Event, Effect, State> = storeFactory()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_outgoing_request_details, container, false)

        val bundle = arguments
        val requestId : String? = bundle?.getString("requestId")

        if (requestId != "") {
            store.accept(
                Event.Ui.GetRequestDetails(requestId!!)
            )
        }
        else {
            store.accept(
                Event.Internal.ErrorDetailsLoading
            )
        }


        activity?.findViewById<ImageButton>(R.id.button_start_date)!!.setOnClickListener {
            store.accept(
                Event.Ui.OnCalendarClicked(activity?.findViewById<TextView>(R.id.outgoing_start_date)!!.text.toString())
            )
        }

        activity?.findViewById<ImageButton>(R.id.button_end_date)!!.setOnClickListener {
            store.accept(
                Event.Ui.OnCalendarClicked(activity?.findViewById<TextView>(R.id.outgoing_end_date)!!.text.toString())
            )
        }


        activity?.findViewById<ImageButton>(R.id.button_destination)!!.setOnClickListener {
            store.accept(
                Event.Ui.OnMapClicked(activity?.findViewById<TextView>(R.id.outgoing_destination)!!.text.toString())
            )
        }


        return rootView
    }

    override fun render(state: State) {
        Log.i("STATE", "render state")
    }

    override fun handleEffect(effect: Effect) = when (effect) {  //обрабатывает side Effect
        is Effect.ShowErrorLoading -> Toast.makeText(activity, "Can not show the request details", Toast.LENGTH_SHORT).show()
        is Effect.ShowRequestDetails -> ShowRequestDetails(effect.request)
        is Effect.ShowCalendar -> {
            TODO()
        }
        is Effect.ShowMap -> {
            TODO()
        }
    }

    private fun ShowRequestDetails(request: Request) {
        activity?.findViewById<TextView>(R.id.outgoing_id)!!.text = request.id
        activity?.findViewById<TextView>(R.id.outgoing_status)!!.text = request.requestStatus
        activity?.findViewById<TextView>(R.id.outgoing_description)!!.text = request.description
        activity?.findViewById<TextView>(R.id.outgoing_start_date)!!.text =  DateToString(request.startDate)
        activity?.findViewById<TextView>(R.id.outgoing_end_date)!!.text = DateToString(request.endDate)
        activity?.findViewById<TextView>(R.id.outgoing_destination)!!.text = request.destination.office?.address
        activity?.findViewById<TextView>(R.id.outgoing_tickets)!!.text = request.ticketsUrl
        activity?.findViewById<TextView>(R.id.outgoing_comment)!!.text = request.comment
    }


    private fun DateToString(date: Date?) : String? {
        val OLD_FORMAT = "yyyy-MM-dd"
        val NEW_FORMAT = "dd.MM.yyyy"
        val format = SimpleDateFormat(OLD_FORMAT)
        val oldDateString : String? = date?.let { format.format(it) }
        val newDateString: String
        val d: Date = format.parse(oldDateString)
        format.applyPattern(NEW_FORMAT)
        newDateString = format.format(d)
        return newDateString
    }
}