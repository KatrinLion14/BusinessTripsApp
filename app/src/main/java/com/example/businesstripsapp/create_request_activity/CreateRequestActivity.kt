package com.example.businesstripsapp.create_request_activity

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.businesstripsapp.R
import com.example.businesstripsapp.create_request_activity.domain.models.Destination
import com.example.businesstripsapp.create_request_activity.domain.models.Request
import com.example.businesstripsapp.create_request_activity.presentation.Effect
import com.example.businesstripsapp.create_request_activity.presentation.Event
import com.example.businesstripsapp.create_request_activity.presentation.State
import com.example.businesstripsapp.create_request_activity.presentation.storeFactory
import org.json.JSONObject
import vivid.money.elmslie.android.base.ElmActivity
import vivid.money.elmslie.core.store.Store
import java.text.SimpleDateFormat
import java.util.Base64
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.xml.datatype.DatatypeConstants.MONTHS

class CreateRequestActivity : ElmActivity<Event, Effect, State>(R.layout.activity_request_create) {
    override val initEvent: Event = Event.Ui.Init
    private var createButton: Button? = null
    private var progressBar: RelativeLayout? = null
    var tripDescription = findViewById<EditText?>(R.id.request_description_data)
    var startDate = findViewById<TextView?>(R.id.request_start_date)
    var endDate = findViewById<TextView>(R.id.request_end_date)
    var destinationDescription = findViewById<EditText>(R.id.destination_description_data)
    var destinationOfficeId = findViewById<EditText>(R.id.destination_office_id_data)
    var destinationSeatPlace = findViewById<EditText>(R.id.destination_seat_place_data)
    var ticketURL = findViewById<EditText>(R.id.ticket_URL_data)
    var comment = findViewById<EditText>(R.id.comment_data)

    override fun createStore(): Store<Event, Effect, State> = storeFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_create)
        createButton = findViewById(R.id.button_create)
        progressBar = findViewById(R.id.progressBarContainer)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.apply {
            title = "Создание заявки"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
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

        createButton?.setOnClickListener {
            tripDescription = findViewById(R.id.request_description_data)
            startDate = findViewById(R.id.request_start_date)
            endDate = findViewById(R.id.request_end_date)
            destinationDescription = findViewById(R.id.destination_description_data)
            destinationOfficeId = findViewById(R.id.destination_office_id_data)
            destinationSeatPlace = findViewById(R.id.destination_seat_place_data)
            ticketURL = findViewById(R.id.ticket_URL_data)
            comment = findViewById(R.id.comment_data)
            if (!isFieldEmpty(destinationOfficeId) && !isFieldEmpty(destinationSeatPlace)) {
                store.accept(
                    Event.Ui.OnCreateClicked(
                        Destination(
                            id = null,
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
            createButton?.isClickable = false
        } else {
            progressBar?.visibility = View.INVISIBLE
            createButton?.isClickable = true
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun handleEffect(effect: Effect) = when (effect) {
        is Effect.ShowErrorNetwork -> Toast.makeText(
            this,
            "Problems with your connection",
            Toast.LENGTH_SHORT
        ).show()


        is Effect.DestinationCreated -> {
            val destinationId = effect.createDestination.id

            val token = intent?.getStringExtra("token") ?: ""
            val userId = getUserId(token)

            tripDescription = findViewById(R.id.request_description_data)
            startDate = findViewById(R.id.request_start_date)
            endDate = findViewById(R.id.request_end_date)
            destinationDescription = findViewById(R.id.destination_description_data)
            destinationOfficeId = findViewById(R.id.destination_office_id_data)
            destinationSeatPlace = findViewById(R.id.destination_seat_place_data)
            ticketURL = findViewById(R.id.ticket_URL_data)
            comment = findViewById(R.id.comment_data)
            store.accept(
                Event.Ui.OnDestinationCreated(
                    Request(
                        id = null,
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
        is Effect.ShowErrorCreateDestination -> Toast.makeText(
            this,
            "Check destination fields (office id, seat place)",
            Toast.LENGTH_SHORT
        ).show()
        is Effect.ShowErrorCreateRequest -> Toast.makeText(
            this,
            "Unexpected error while creating",
            Toast.LENGTH_SHORT
        ).show()
        is Effect.ToRequestsActivity -> {
            finish()
        }
        is Effect.ShowCalendarStartDate -> {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                var date : String = dayOfMonth.toString() + "." + (month+1).toString() + "." + year.toString()
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

                var date : String = dayOfMonth.toString() + "." + (month+1).toString() + "." + year.toString()
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

    private fun DateToString(date: Date) : String {
        val OLD_FORMAT = "yyyy-MM-dd"
        val NEW_FORMAT = "dd.MM.yyyy"
        val format = SimpleDateFormat(OLD_FORMAT, Locale.getDefault())
        val oldDateString : String = format.format(date)
        val newDateString: String
        val d: Date = format.parse(oldDateString) as Date
        format.applyPattern(NEW_FORMAT)
        newDateString = format.format(d)
        return newDateString
    }

    private fun StringToDate(string: String) : Date {
        val FORMAT = "dd.MM.yyyy"
        val format = SimpleDateFormat(FORMAT, Locale.getDefault())
        val date: Date = format.parse(string) as Date
        return date
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getUserId(token: String) : String {
        val splitString = token.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        val base64EncodedBody = splitString[1]
        val body = String(Base64.getDecoder().decode(base64EncodedBody))
        val jsonObject = JSONObject(body)

        return jsonObject["id"].toString()
    }
}