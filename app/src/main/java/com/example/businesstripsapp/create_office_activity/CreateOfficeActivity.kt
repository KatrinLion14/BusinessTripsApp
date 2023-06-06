package com.example.businesstripsapp.create_office_activity

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.businesstripsapp.R
import com.example.businesstripsapp.create_office_activity.domain.models.Office
import com.example.businesstripsapp.create_office_activity.presentation.Effect
import com.example.businesstripsapp.create_office_activity.presentation.Event
import com.example.businesstripsapp.create_office_activity.presentation.State
import com.example.businesstripsapp.create_office_activity.presentation.storeFactory
import vivid.money.elmslie.android.base.ElmActivity
import vivid.money.elmslie.core.store.Store
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

class CreateOfficeActivity : ElmActivity<Event, Effect, State>(R.layout.activity_create_office) {
    override val initEvent: Event = Event.Ui.Init
    private var createButton: Button? = null
    private var progressBar: FrameLayout? = null

    override fun createStore(): Store<Event, Effect, State> = storeFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_office)
        createButton = findViewById(R.id.buttonContinue)
        progressBar = findViewById(R.id.progressBarContainer)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.apply {
            title = "Добавление офиса"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        val addressText = findViewById<EditText>(R.id.officeAddressData)
        val descriptionText = findViewById<EditText>(R.id.officeDescriptionData)
        createButton?.setOnClickListener {
            if (!isFieldEmpty(addressText) && !isFieldEmpty(descriptionText)) {
                store.accept(
                    Event.Ui.CreateClick(
                        Office(
                            address = addressText.text.toString(),
                            description = descriptionText.text.toString()
                        )
                    )
                )
            }
        }
    }

    override fun render(state: State) {
        if (state.loading) {
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

        is Effect.ShowErrorCreateOffice -> Toast.makeText(
            this,
            "Unexpected error while creating",
            Toast.LENGTH_SHORT
        ).show()

        is Effect.ReturnToHome -> {
            Toast.makeText(
                this,
                "Office created",
                Toast.LENGTH_SHORT
            ).show()
            this.finish()
        }

        is Effect.SaveOfficeId -> {
            SaveId(effect.office)
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

    private fun SaveId(office: Office) {
        //TODO
    }
}