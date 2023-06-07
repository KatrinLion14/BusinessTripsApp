package com.example.businesstripsapp.create_request_activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.auth0.android.jwt.JWT
import com.example.businesstripsapp.R
import com.example.businesstripsapp.create_office_activity.roomDB.AppDatabase
import com.example.businesstripsapp.create_office_activity.roomDB.OfficeDB
import com.example.businesstripsapp.create_request_activity.domain.models.Destination
import com.example.businesstripsapp.create_request_activity.domain.models.Request
import com.example.businesstripsapp.create_request_activity.presentation.Effect
import com.example.businesstripsapp.create_request_activity.presentation.Event
import com.example.businesstripsapp.create_request_activity.presentation.State
import com.example.businesstripsapp.create_request_activity.presentation.storeFactory
import com.example.businesstripsapp.network.NetworkService
import vivid.money.elmslie.android.base.ElmActivity
import vivid.money.elmslie.core.store.Store
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class CreateRequestActivity : ElmActivity<Event, Effect, State>(R.layout.activity_request_create) {

    override val initEvent: Event = Event.Ui.Init

    private var db: AppDatabase? = null
    private var officeDB: OfficeDB? = null
    //private var officesList: List<String?>? = null
    private var officeAdapter: ArrayAdapter<String>? = null

    override fun createStore(): Store<Event, Effect, State> = storeFactory()

    private val token = NetworkService.instance.getToken()
    private val jwt: JWT = JWT(token)
    private val userId: String = jwt.getClaim("id").asString() ?: ""

    private var createButton: Button? = null
    private var progressBar: FrameLayout? = null

    private var tripDescription: EditText? = null
    private var startDate: TextView? = null
    private var endDate: TextView? = null
    private var destinationDescription: EditText? = null
    private var destinationOfficeId: String? = null
    private var destinationSeatPlace: EditText? = null
    private var ticketURL: EditText? = null
    private var comment: EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_create)
        createButton = findViewById(R.id.button_create)
        progressBar = findViewById(R.id.progressBarContainer)

        db = AppDatabase.build(applicationContext)
        officeDB = OfficeDB()
        val officesList: List<String?>? = db?.officeDao()?.idsList()
        val offices: List<String?> = getOfficesIds(officesList)

        officeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, offices)

        val officeSpinner: Spinner = findViewById(R.id.office_spinner)
        officeSpinner.adapter = officeAdapter

        val itemSelectedListener: AdapterView.OnItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    destinationOfficeId = offices[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        officeSpinner.onItemSelectedListener = itemSelectedListener


        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.apply {
            title = "Создание заявки"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        tripDescription = findViewById(R.id.request_description_data)
        startDate = findViewById(R.id.request_start_date)
        endDate = findViewById(R.id.request_end_date)
        destinationDescription = findViewById(R.id.destination_description_data)
        destinationSeatPlace = findViewById(R.id.destination_seat_place_data)
        ticketURL = findViewById(R.id.ticket_URL_data)
        comment = findViewById(R.id.comment_data)


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
            updateFromEditText()
            if ((destinationOfficeId != null) && !isFieldEmpty(destinationSeatPlace!!)) {
                store.accept(
                    Event.Ui.OnCreateClicked(
                        Destination(
                            id = null,
                            description = destinationDescription!!.text.toString(),
                            officeId = destinationOfficeId,
                            seatPlace = destinationSeatPlace!!.text.toString()
                        )
                    )
                )
            }
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

    override fun render(state: State) {
        if (state.isLoading) {
            progressBar?.visibility = View.VISIBLE
            createButton?.isClickable = false
        } else {
            progressBar?.visibility = View.INVISIBLE
            createButton?.isClickable = true
        }
    }

    override fun handleEffect(effect: Effect) = when (effect) {
        is Effect.ShowErrorNetwork -> Toast.makeText(
            this,
            "Problems with your connection",
            Toast.LENGTH_SHORT
        ).show()


        is Effect.DestinationCreated -> {
            val destinationId = effect.createDestination.id

            updateFromEditText()
            store.accept(
                Event.Ui.OnDestinationCreated(
                    Request(
                        id = null,
                        description = tripDescription?.text.toString(),
                        workerId = userId,
                        destinationId = destinationId,
                        comment = comment?.text.toString(),
                        startDate = StringToDate(startDate?.text.toString()),
                        endDate = StringToDate(endDate?.text.toString()),
                        ticketsUrl = ticketURL?.text.toString()
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
                startDate?.setText(date)
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
                endDate?.setText(date)

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

    private fun updateFromEditText() {
        tripDescription = findViewById(R.id.request_description_data)
        startDate = findViewById(R.id.request_start_date)
        endDate = findViewById(R.id.request_end_date)
        destinationDescription = findViewById(R.id.destination_description_data)
        destinationSeatPlace = findViewById(R.id.destination_seat_place_data)
        ticketURL = findViewById(R.id.ticket_URL_data)
        comment = findViewById(R.id.comment_data)
    }

    private fun getOfficesIds(array: List<String?>?): ArrayList<String> {
        val res = ArrayList<String>()
        if (array != null) {
            for (e in array) {
                val officeId: String = e.toString()
                res.add(officeId)
            }
        }
        return res
    }
    private fun getOffices(array: List<OfficeDB?>?): ArrayList<String> {
        val res = ArrayList<String>()
        if (array != null) {
            for (e in array) {
                val office: String = e.toString()
                res.add(office)
            }
        }
        return res
    }
}