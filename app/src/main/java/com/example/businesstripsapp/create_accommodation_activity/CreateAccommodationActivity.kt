package com.example.businesstripsapp.create_accommodation_activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import com.example.businesstripsapp.R
import com.example.businesstripsapp.create_accommodation_activity.domain.models.Accommodation
import com.example.businesstripsapp.create_accommodation_activity.presentation.Effect
import com.example.businesstripsapp.create_accommodation_activity.presentation.Event
import com.example.businesstripsapp.create_accommodation_activity.presentation.State
import com.example.businesstripsapp.create_accommodation_activity.presentation.storeFactory
import com.example.businesstripsapp.create_request_activity.CreateRequestActivity
import com.example.businesstripsapp.request_edit_activity.RequestEditActivity
import vivid.money.elmslie.android.base.ElmActivity
import vivid.money.elmslie.core.store.Store

class CreateAccommodationActivity : ElmActivity<Event, Effect, State>(R.layout.activity_accommodation_create) {
    override val initEvent: Event = Event.Ui.Init
    private var createButton: Button? = null
    private var progressBar: FrameLayout? = null

    override fun createStore(): Store<Event, Effect, State> = storeFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accommodation_create)
        createButton = findViewById(R.id.buttonContinue)
        progressBar = findViewById(R.id.progressBarContainer)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.apply {
            title = "Добавление размещения"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        val addressText = findViewById<EditText>(R.id.accommodationAddressData)
        val descriptionText = findViewById<EditText>(R.id.accommodationBookingURLData)
        createButton?.setOnClickListener {
            if (!isFieldEmpty(addressText) && !isFieldEmpty(descriptionText)) {
                store.accept(
                    Event.Ui.CreateClick(
                        Accommodation(
                            address = addressText.text.toString(),
                            bookingUrl = descriptionText.text.toString()
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

    override fun handleEffect(effect: Effect) = when (effect) {
        is Effect.ShowErrorNetwork -> Toast.makeText(
            this,
            "Problems with your connection",
            Toast.LENGTH_SHORT
        ).show()

        is Effect.ShowErrorCreateAccommodation -> Toast.makeText(
            this,
            "Unexpected error while creating",
            Toast.LENGTH_SHORT
        ).show()

        is Effect.ToPreviousActivity -> {
            Toast.makeText(
                this,
                "Accommodation created",
                Toast.LENGTH_SHORT
            ).show()
            val intent: Intent = Intent(this, CreateRequestActivity::class.java)
            intent.putExtra("accomodationId", effect.createAccommodation.id)
            startActivity(intent)
            this.finish()
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
}