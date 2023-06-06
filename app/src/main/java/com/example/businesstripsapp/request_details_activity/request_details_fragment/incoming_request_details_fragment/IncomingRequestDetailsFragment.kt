package com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.auth0.android.jwt.JWT
import com.example.businesstripsapp.R
import com.example.businesstripsapp.network.NetworkService
import com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.domain.models.Accommodation
import com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.domain.models.CreateAccommodation
import com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.domain.models.Request
import com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.domain.models.RequestChangeStatus
import com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.domain.models.Trip
import com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.presentation.Effect
import com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.presentation.Event
import com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.presentation.State
import com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.presentation.storeFactory
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.core.store.Store
import java.text.SimpleDateFormat
import java.util.Date


class IncomingRequestDetailsFragment: ElmFragment<Event, Effect, State>(R.layout.fragment_incoming_request_details) {
    override val initEvent: Event = Event.Ui.Init //событие инициализации экрана
    private var accommodation_address: String? = null
    private var accommodation_url: String? = null
    private var destinationId: String? = null
    private var requestId : String? = null
    private var userId : String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_incoming_request_details, container, false)

        val token = NetworkService.instance.getToken()
        val jwt = JWT(token)
        userId = jwt.getClaim("id").asString() ?: ""

        val bundle = arguments
        requestId = bundle?.getString("requestId")

        if (requestId != "") {
            store.accept(
                Event.Ui.ShowRequestDetails(requestId!!)
            )
        }
        else {
            store.accept(
                Event.Internal.ErrorDetailsLoading
            )
        }

        activity?.findViewById<ImageButton>(R.id.button_start_date)!!.setOnClickListener {
            store.accept(
                Event.Ui.OnCalendarClicked(activity?.findViewById<TextView>(R.id.incoming_start_date)!!.text.toString())
            )
        }

        activity?.findViewById<ImageButton>(R.id.button_end_date)!!.setOnClickListener {
            store.accept(
                Event.Ui.OnCalendarClicked(activity?.findViewById<TextView>(R.id.incoming_end_date)!!.text.toString())
            )
        }


        activity?.findViewById<ImageButton>(R.id.button_destination)!!.setOnClickListener {
            store.accept(
                Event.Ui.OnMapClicked(activity?.findViewById<TextView>(R.id.incoming_destination)!!.text.toString())
            )
        }

        activity?.findViewById<ImageButton>(R.id.incoming_button_approve)!!.setOnClickListener {
            store.accept(
                Event.Ui.OnApproveClicked
            )
        }

        activity?.findViewById<ImageButton>(R.id.incoming_button_send_back)!!.setOnClickListener {
            store.accept(
                Event.Ui.OnSendBackClicked(requestId!!,
                    RequestChangeStatus(
                        approverId = userId!!,
                        tripDTo = null,
                        comment = activity?.findViewById<TextView>(R.id.incoming_comment)!!.text.toString()  //todo comment window
                    )
                )
            )
        }

        activity?.findViewById<ImageButton>(R.id.incoming_button_decline)!!.setOnClickListener {
            store.accept(
                Event.Ui.OnSendBackClicked(requestId!!,
                    RequestChangeStatus(
                        approverId = userId!!,
                        tripDTo = null,
                        comment = activity?.findViewById<TextView>(R.id.incoming_comment)!!.text.toString()  //todo comment window
                    )
                )
            )
        }

        return rootView
    }

    override fun createStore(): Store<Event, Effect, State> = storeFactory()

    override fun render(state: State) {
        Log.i("STATE", "render state")
    }


    override fun handleEffect(effect: Effect) = when (effect) {  //обрабатывает side Effect
        is Effect.ShowErrorLoading -> Toast.makeText(
            activity,
            "Can not show the request details",
            Toast.LENGTH_SHORT)
            .show()
        is Effect.ShowRequestDetails -> ShowRequestDetails(effect.request)
        is Effect.ShowDialog -> {
            ShowDialog()
        }
        is Effect.AccommodationCreated -> {
            store.accept(
                Event.Ui.OnAccommodationCreated(
                    requestId = requestId!!,
                    RequestChangeStatus(
                        approverId = userId!!,
                        tripDTo = Trip (
                                    id = null,
                                    tripStatus = "pending",
                                    accommodationId = effect.createAccommodation.id,
                                    destinationId = destinationId,
                                    requestId = requestId
                                ),
                        comment = activity?.findViewById<TextView>(R.id.incoming_comment)!!.text.toString()
                    )
                )
            )
        }
        //is Effect.CloseDialog -> TODO()
        //is Effect.TripCreated -> {
        //    store.accept(
        //        Event.Ui.OnTripCreated(requestId!!,
        //            RequestChangeStatus()
        //          )
        //    )
        //}
        is Effect.ShowErrorApproving -> Toast.makeText(
            activity,
            "Approving error",
            Toast.LENGTH_SHORT)
            .show()
        is Effect.ShowErrorCreateAccommodation -> Toast.makeText(
            activity,
            "Can not create accommodation",
            Toast.LENGTH_SHORT)
            .show()
        //is Effect.ShowErrorCreateTrip -> TODO()
        is Effect.ShowSuccessStatusUpdating -> Toast.makeText(
            activity,
            "Status has been changed",
            Toast.LENGTH_SHORT)
            .show()
        is Effect.ShowErrorDeclining -> Toast.makeText(
            activity,
            "Declining error",
            Toast.LENGTH_SHORT)
            .show()
        is Effect.ShowErrorSendingBack -> Toast.makeText(
            activity,
            "Error: Status has not been changed",
            Toast.LENGTH_SHORT)
            .show()
        is Effect.ShowCalendar -> TODO()
        is Effect.ShowMap -> TODO()
    }

    private fun ShowRequestDetails(request: Request) {
        activity?.findViewById<TextView>(R.id.incoming_id)!!.text = request.id
        activity?.findViewById<TextView>(R.id.incoming_name)!!.text = request.workerFirstName + request.workerSecondName
        activity?.findViewById<TextView>(R.id.incoming_status)!!.text = request.requestStatus
        activity?.findViewById<TextView>(R.id.incoming_description)!!.text = request.description
        activity?.findViewById<TextView>(R.id.incoming_start_date)!!.text =  DateToString(request.startDate)
        activity?.findViewById<TextView>(R.id.incoming_end_date)!!.text = DateToString(request.endDate)
        activity?.findViewById<TextView>(R.id.incoming_destination)!!.text = request.destination?.office?.address
        activity?.findViewById<TextView>(R.id.incoming_seat_place)!!.text = request.destination?.seatPlace
        activity?.findViewById<TextView>(R.id.incoming_tickets)!!.text = request.ticketsUrl
        activity?.findViewById<TextView>(R.id.incoming_comment)!!.text = request.comment

        destinationId = request.destination?.id
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

    private fun ShowDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        val viewInflated: View = LayoutInflater.from(activity)
            .inflate(R.layout.dialog_accommodation_create, view as ViewGroup?, false)
        val accommodationAddress = viewInflated.findViewById<View>(R.id.accommodation_address_data) as EditText
        val accommodationURL = viewInflated.findViewById<View>(R.id.accommodation_bookingURL_data) as EditText
        val createButton = activity?.findViewById<ImageButton>(R.id.button_create)
        val cancelButton = activity?.findViewById<ImageButton>(R.id.button_cancel)

        builder.setView(viewInflated)

        createButton?.setOnClickListener {
            DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
                accommodation_address = accommodationAddress.text.toString()
                accommodation_url = accommodationURL.text.toString()
                store.accept(
                    Event.Ui.OnCreateAccommodationClicked(
                        Accommodation(
                            id = null,
                            address = accommodation_address,
                            bookingUrl = accommodation_url
                        )
                    )
                )
            }
        }

        cancelButton?.setOnClickListener {
            DialogInterface.OnClickListener { dialog, _ -> dialog.cancel() }
        }

        builder.show()
    }

}