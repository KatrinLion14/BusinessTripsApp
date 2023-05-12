package com.example.businesstripsapp.subordinate_details_activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.businesstripsapp.R
import com.example.businesstripsapp.main_activity.domain.models.User

class SubordinateDetailsActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subordinate_details)

        val user = intent.extras?.getSerializable("subordinate") as User
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.apply {
            title = "Профиль сотрудника"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        val emailText = findViewById<TextView>(R.id.subordinateEmailData)
        emailText.text = user.email
        val subordinateName = findViewById<TextView>(R.id.subordinateName)
        subordinateName.text = "${user.firstName} ${user.secondName}"
    }
}