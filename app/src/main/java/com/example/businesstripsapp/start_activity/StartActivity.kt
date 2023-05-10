package com.example.businesstripsapp.start_activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.businesstripsapp.start_activity.presentation.Effect
import com.example.businesstripsapp.start_activity.presentation.Event
import com.example.businesstripsapp.R
import com.example.businesstripsapp.main_activity.MainActivity
import com.example.businesstripsapp.main_activity.domain.UserRepository
import com.example.businesstripsapp.network.NetworkService
import com.example.businesstripsapp.start_activity.presentation.State
import com.example.businesstripsapp.start_activity.presentation.storeFactory
import vivid.money.elmslie.android.base.ElmActivity
import vivid.money.elmslie.core.store.Store

class StartActivity : ElmActivity<Event, Effect, State>(R.layout.activity_start) {
    override val initEvent: Event = Event.Ui.Init

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val editTextEmail = findViewById<EditText>(R.id.etEmail)
        val editTextPassword = findViewById<EditText>(R.id.etPassword)

        val buttonStart = findViewById<Button>(R.id.buttonContinue)
        buttonStart.setOnClickListener {
            store.accept(
                Event.Ui.ClickContinue(
                    editTextEmail.text.toString(),
                    editTextPassword.text.toString()
                )
            )
        }
    }

    override fun createStore(): Store<Event, Effect, State> = storeFactory()

    override fun render(state: State) {
        Log.i("STATE", "render state")
    }

    override fun handleEffect(effect: Effect) = when (effect) {
        Effect.ShowAuthError -> Toast.makeText(applicationContext, "Invalid e-mail or password", Toast.LENGTH_SHORT).show()
        is Effect.ToMainActivity -> toUserMainActivity(effect.token)
    }

    private fun toUserMainActivity(token: String) {
        NetworkService.instance.setService(token)
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
