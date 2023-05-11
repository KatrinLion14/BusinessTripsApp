package com.example.businesstripsapp.notification_activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.auth0.android.jwt.JWT
import com.example.businesstripsapp.R
import com.example.businesstripsapp.main_activity.domain.models.Notification
import com.example.businesstripsapp.main_activity.domain.models.Request
import com.example.businesstripsapp.main_activity.domain.models.constant.RequestStatus
import com.example.businesstripsapp.network.NetworkService
import com.example.businesstripsapp.notification_activity.presentation.Effect
import com.example.businesstripsapp.notification_activity.presentation.Event
import com.example.businesstripsapp.notification_activity.presentation.State
import com.example.businesstripsapp.notification_activity.presentation.storeFactory
import com.example.businesstripsapp.notification_details_activity.NotificationDetailsActivity
import vivid.money.elmslie.android.base.ElmActivity
import vivid.money.elmslie.core.store.Store

class NotificationActivity : ElmActivity<Event, Effect, State>(R.layout.activity_notification) {
    private val token = NetworkService.instance.getToken()
    private val jwt: JWT = JWT(token)
    val id: String = jwt.getClaim("id").asString() ?: ""

    override val initEvent: Event = Event.Ui.Init(id)
    override fun createStore(): Store<Event, Effect, State> = storeFactory()

    private var refreshLayout: SwipeRefreshLayout? = null
    private var notificationRecycler: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        refreshLayout = findViewById(R.id.refreshLayout)
        notificationRecycler = findViewById(R.id.notificationRecycler)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.apply {
            title = "Уведомления"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

//        val list = listOf(
//            Notification(
//                "123",
//                true,
//                "123",
//                Request(
//                    Request.Destination((Request.Office(("Moscow")))),
//                    "14.03.2000",
//                    RequestStatus.PENDING,
//                    "15.03.2001",
//                    "test",
//                    "test"
//                )
//            )
//        )
//        val layoutManager = LinearLayoutManager(this)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        notificationRecycler?.layoutManager = layoutManager
//        notificationRecycler?.adapter = NotificationsRecyclerAdapter(list, store)
    }

    override fun render(state: State) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        notificationRecycler?.layoutManager = layoutManager
        notificationRecycler?.adapter = NotificationsRecyclerAdapter(state.notifications, store)

        refreshLayout?.isRefreshing = state.loading
    }

    override fun handleEffect(effect: Effect) = when (effect) {
        Effect.ShowErrorNetwork -> Toast.makeText(
            this,
            "Unexpected problems on the server",
            Toast.LENGTH_SHORT
        ).show()

        Effect.ShowLoadingError -> Toast.makeText(
            this,
            "Problems with your Internet connection",
            Toast.LENGTH_SHORT
        ).show()

        is Effect.ToNotificationDetails -> toNotificationDetails(effect.request)
    }

    private fun toNotificationDetails(request: Request) {
        val intent = Intent(this, NotificationDetailsActivity::class.java)
        intent.putExtra("request", request)
        startActivity(intent)
    }
}