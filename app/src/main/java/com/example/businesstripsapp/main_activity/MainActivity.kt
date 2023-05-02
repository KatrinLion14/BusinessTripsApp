package com.example.businesstripsapp.main_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.businesstripsapp.R
import com.example.businesstripsapp.trips_activity.TripsActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val token = intent.getStringExtra("token")

        val button = findViewById<Button>(R.id.button3)

        button.setOnClickListener {
            val intent = Intent(this, TripsActivity::class.java).apply {
                putExtra("token", token)
            }
            startActivity(intent)
        }
    }
}