package com.example.businesstripsapp.create_user_activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import com.example.businesstripsapp.R
import com.example.businesstripsapp.create_user_activity.presentation.Effect
import com.example.businesstripsapp.create_user_activity.presentation.Event
import com.example.businesstripsapp.create_user_activity.presentation.State
import com.example.businesstripsapp.create_user_activity.presentation.storeFactory
import com.example.businesstripsapp.main_activity.domain.models.User
import vivid.money.elmslie.android.base.ElmActivity
import vivid.money.elmslie.core.store.Store

class CreateUserActivity : ElmActivity<Event, Effect, State>(R.layout.activity_create_user) {
    override val initEvent: Event = Event.Ui.Init
    private var createButton: Button? = null
    private var progressBar: FrameLayout? = null

    override fun createStore(): Store<Event, Effect, State> = storeFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
        createButton = findViewById(R.id.buttonContinue)
        progressBar = findViewById(R.id.progressBarContainer)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.apply {
            title = "Добавление пользователя"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        val emailText = findViewById<EditText>(R.id.userEmailData)
        val passwordText = findViewById<EditText>(R.id.userPasswordData)
        val firstNameText = findViewById<EditText>(R.id.firstNameData)
        val secondNameText = findViewById<EditText>(R.id.secondNameData)
        val roleText = findViewById<EditText>(R.id.roleData)
        val subordinateId = findViewById<EditText>(R.id.subordinatesData)
        createButton?.setOnClickListener {
            if (!isFieldEmpty(emailText) && !isFieldEmpty(passwordText) &&
                !isFieldEmpty(firstNameText) && !isFieldEmpty(secondNameText) && !isFieldEmpty(roleText)
            ) {
                store.accept(
                    Event.Ui.CreateClick(
                        User(
                            email = emailText.text.toString(),
                            password = passwordText.text.toString(),
                            firstName = firstNameText.text.toString(),
                            secondName = secondNameText.text.toString(),
                            userRole = roleText.text.toString()
                        ),
                        subordinateId.text.toString()
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
        is Effect.ShowErrorGetSubordinates -> Toast.makeText(
            this,
            "User will be created without subordinates",
            Toast.LENGTH_SHORT
        ).show()
        is Effect.ShowErrorCreateUser -> Toast.makeText(
            this,
            "Unexpected error while creating",
            Toast.LENGTH_SHORT
        ).show()
        is Effect.ReturnToHome -> {
            Toast.makeText(
                this,
                "User created",
                Toast.LENGTH_SHORT
            ).show()
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