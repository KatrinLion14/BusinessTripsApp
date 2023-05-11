package com.example.businesstripsapp.notification_details_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.businesstripsapp.R
import com.example.businesstripsapp.main_activity.domain.models.Request
import com.example.businesstripsapp.main_activity.domain.models.constant.RequestStatus

class NotificationDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_details)

        val request = intent.extras?.getSerializable("request") as Request

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.apply {
            title = "Детали запроса"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        val startDate = findViewById<TextView>(R.id.startDateData)
        startDate.text = request.startDate
        val endDate = findViewById<TextView>(R.id.endDateData)
        endDate.text = request.endDate
        val destination = findViewById<TextView>(R.id.destinationData)
        destination.text = request.destination.office.address
        val status = findViewById<TextView>(R.id.statusData)
        when (request.requestStatus) {
            RequestStatus.APPROVED -> {
                status.text = "Утверждена"
            }
            RequestStatus.PENDING -> {
                status.text = "В обработке"
            }
            RequestStatus.AWAIT_CHANGES -> {
                status.text = "Возвращена"
            }
            RequestStatus.DECLINED -> {
                status.text = "Отклонена"
            }
        }
    }
}