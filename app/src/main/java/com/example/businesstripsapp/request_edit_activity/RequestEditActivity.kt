package com.example.businesstripsapp.request_edit_activity

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.auth0.android.jwt.JWT
import com.example.businesstripsapp.R
import com.example.businesstripsapp.network.NetworkService
import com.example.businesstripsapp.request_edit_activity.domain.models.DestinationUpdate
import com.example.businesstripsapp.request_edit_activity.domain.models.Request
import com.example.businesstripsapp.request_edit_activity.domain.models.RequestUpdate
import com.example.businesstripsapp.request_edit_activity.presentation.Effect
import com.example.businesstripsapp.request_edit_activity.presentation.Event
import com.example.businesstripsapp.request_edit_activity.presentation.State
import com.example.businesstripsapp.request_edit_activity.presentation.storeFactory
import vivid.money.elmslie.android.base.ElmActivity
import vivid.money.elmslie.core.store.Store
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class RequestEditActivity : ElmActivity<Event, Effect, State>(R.layout.activity_request_edit) {
    override val initEvent: Event = Event.Ui.Init

    private var saveButton: Button? = null
    private var progressBar: RelativeLayout? = null
    var requestId: String? = null
    var destinationId: String? = null
    var tripDescription: EditText = findViewById(R.id.request_description_data)
    var startDate: TextView = findViewById(R.id.request_start_date)
    var endDate: TextView = findViewById(R.id.request_end_date)
    var destinationDescription: EditText = findViewById(R.id.destination_description_data)
    var destinationOfficeId: EditText = findViewById(R.id.destination_office_id_data)
    var destinationSeatPlace: EditText = findViewById(R.id.destination_seat_place_data)
    var ticketURL: EditText = findViewById(R.id.ticket_URL_data)
    var comment: EditText = findViewById(R.id.comment_data)

    override fun createStore(): Store<Event, Effect, State> = storeFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_create)
        saveButton = findViewById(R.id.button_create)
        progressBar = findViewById(R.id.progressBarContainer)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.apply {
            title = "Редактирование заявки"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        requestId = intent.getStringExtra("requestId")

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

        findViewById<ImageButton>(R.id.start_date_button)!!.setOnClickListener {
            store.accept(
                Event.Ui.OnCalendarStartDateClicked(findViewById<TextView>(R.id.request_start_date)!!.text.toString())
            )
        }

        findViewById<ImageButton>(R.id.end_date_button)!!.setOnClickListener {
            store.accept(
                Event.Ui.OnCalendarEndDateClicked(findViewById<TextView>(R.id.request_end_date)!!.text.toString())
            )
        }

        saveButton?.setOnClickListener {
            updateFromEditText()
            if (!isFieldEmpty(destinationOfficeId) && !isFieldEmpty(destinationSeatPlace)) {
                store.accept(
                    Event.Ui.OnSaveClicked(
                        DestinationUpdate(
                            id = destinationId,
                            description = destinationDescription.text.toString(),
                            officeId = destinationOfficeId.text.toString(),
                            seatPlace = destinationSeatPlace.text.toString()
                        )
                    )
                )
            }
        }
    }

    override fun render(state: State) {
        if (state.isLoading) {
            progressBar?.visibility = View.VISIBLE
            saveButton?.isClickable = false
        } else {
            progressBar?.visibility = View.INVISIBLE
            saveButton?.isClickable = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                store.accept(
                    Event.Ui.OnBackClicked
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun handleEffect(effect: Effect) = when (effect) {

        is Effect.ShowErrorLoading -> Toast.makeText(this, "Can not show the request details", Toast.LENGTH_SHORT).show()

        is Effect.ShowRequestDetails -> {

            destinationId = effect.request.destination.id

            findViewById<TextView>(R.id.edit_id_data)!!.text = effect.request.id
            findViewById<TextView>(R.id.edit_id_data)!!.text = effect.request.requestStatus
            tripDescription.setText(effect.request.description)
            startDate.text =  DateToString(effect.request.startDate)
            endDate.text = DateToString(effect.request.endDate)
            destinationDescription.setText(effect.request.destination.description)
            destinationOfficeId.setText(effect.request.destination.office!!.id)
            destinationSeatPlace.setText(effect.request.destination.seatPlace)
            ticketURL.setText(effect.request.ticketsUrl)
            comment.setText(effect.request.comment)
        }
        is Effect.ShowDialog -> {
            ShowDialog()
        }

        is Effect.ShowErrorNetwork -> Toast.makeText(
            this,
            "Problems with your connection",
            Toast.LENGTH_SHORT
        ).show()

        is Effect.DestinationEdited -> {
            val token = NetworkService.instance.getToken()
            val jwt: JWT = JWT(token)
            val userId: String = jwt.getClaim("id").asString() ?: ""

            updateFromEditText()
            store.accept(
                Event.Ui.OnDestinationEdited(
                    RequestUpdate(
                        id = requestId,
                        description = tripDescription.text.toString(),
                        workerId = userId,
                        destinationId = destinationId,
                        comment = comment.text.toString(),
                        startDate = StringToDate(startDate.text.toString()),
                        endDate = StringToDate(endDate.text.toString()),
                        ticketsUrl = ticketURL.text.toString()
                    )
                )
            )
        }
        is Effect.ShowErrorEditDestination -> Toast.makeText(
            this,
            "Check destination fields (office id, seat place)",
            Toast.LENGTH_SHORT
        ).show()
        is Effect.ShowErrorEditRequest -> Toast.makeText(
            this,
            "Unexpected error while creating",
            Toast.LENGTH_SHORT
        ).show()
        is Effect.ToRequestDetailsActivity -> {
            finish()
        }
        is Effect.ShowCalendarStartDate -> {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                val monthString : String = if (monthOfYear+1 < 10) {
                    "0${monthOfYear+1}"
                } else {
                    (monthOfYear+1).toString()
                }
                val dayString : String = if (dayOfMonth < 10) {
                    "0$dayOfMonth"
                } else {
                    dayOfMonth.toString()
                }
                val date : String = "$dayString.$monthString.$year"
                startDate.setText(date)
            }, year, month, day)

            dpd.show()
        }
        is Effect.ShowCalendarEndDate -> {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                val monthString : String = if (monthOfYear+1 < 10) {
                    "0${monthOfYear+1}"
                } else {
                    (monthOfYear+1).toString()
                }
                val dayString : String = if (dayOfMonth < 10) {
                    "0$dayOfMonth"
                } else {
                    dayOfMonth.toString()
                }
                val date : String = "$dayString.$monthString.$year"
                endDate.setText(date)

            }, year, month, day)

            dpd.show()
        }
    }

    private fun isFieldEmpty(etField: EditText): Boolean {
        return if (TextUtils.isEmpty(etField.text.toString())) {
            etField.error = "This field cannot be empty"
            true
        } else {
            false
        }
    }

    private fun StringToDate(string: String): Date {
        val FORMAT = "dd.MM.yyyy"
        val format = SimpleDateFormat(FORMAT, Locale.getDefault())
        return format.parse(string) as Date
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

    private fun updateFromEditText() {
        tripDescription = findViewById(R.id.request_description_data)
        startDate = findViewById(R.id.request_start_date)
        endDate = findViewById(R.id.request_end_date)
        destinationDescription = findViewById(R.id.destination_description_data)
        destinationOfficeId = findViewById(R.id.destination_office_id_data)
        destinationSeatPlace = findViewById(R.id.destination_seat_place_data)
        ticketURL = findViewById(R.id.ticket_URL_data)
        comment = findViewById(R.id.comment_data)
    }

    private fun ShowDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater;
        builder.setView(inflater.inflate(R.layout.dialog_close_edit, null))
        val continueButton = findViewById<ImageButton>(R.id.button_continue)
        val exitButton = findViewById<ImageButton>(R.id.button_exit)

        continueButton?.setOnClickListener {
            DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
            }
        }

        exitButton?.setOnClickListener {
            DialogInterface.OnClickListener { dialog, _ ->
                store.accept(
                    Event.Ui.OnExitClicked
                )
                dialog.cancel()
            }
        }

        builder.show()
    }
}