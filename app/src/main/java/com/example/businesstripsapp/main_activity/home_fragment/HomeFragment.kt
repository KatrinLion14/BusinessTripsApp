package com.example.businesstripsapp.main_activity.home_fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.auth0.android.jwt.JWT
import com.example.businesstripsapp.R
import com.example.businesstripsapp.create_user_activity.CreateUserActivity
import com.example.businesstripsapp.main_activity.home_fragment.presentation.Effect
import com.example.businesstripsapp.main_activity.home_fragment.presentation.Event
import com.example.businesstripsapp.main_activity.home_fragment.presentation.State
import com.example.businesstripsapp.main_activity.home_fragment.presentation.storeFactory
import com.example.businesstripsapp.network.NetworkService
import com.example.businesstripsapp.notification_activity.NotificationActivity
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.core.store.Store

class HomeFragment : ElmFragment<Event, Effect, State>() {
    private val token = NetworkService.instance.getToken()
    private val jwt: JWT = JWT(token)
    private val role: String = jwt.getClaim("role").asString() ?: ""
    val id: String = jwt.getClaim("id").asString() ?: ""

    override fun createStore(): Store<Event, Effect, State> = storeFactory()
    override val initEvent: Event = Event.Ui.Init(id)

    private var requestRecycler: RecyclerView? = null
    private var tripRecycler: RecyclerView? = null
    private var rootView: View? = null
    private var refreshLayout: SwipeRefreshLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.fragment_home, null)
        requestRecycler = rootView?.findViewById(R.id.requestsRecycler)
        tripRecycler = rootView?.findViewById(R.id.tripsRecycler)
        refreshLayout = rootView?.findViewById(R.id.refreshLayout)

        refreshLayout?.setOnRefreshListener {
            store.accept(Event.Ui.PullToRefresh(id))
        }
        val tripsCard = rootView?.findViewById<CardView>(R.id.tripsCard)
        tripsCard?.setOnClickListener {
            store.accept(Event.Ui.ClickTrips)
        }
        val requestCard = rootView?.findViewById<CardView>(R.id.requestsCard)
        requestCard?.setOnClickListener {
            store.accept(Event.Ui.ClickRequests)
        }

        val notificationButton = rootView?.findViewById<ImageButton>(R.id.notificationButton)
        notificationButton?.setOnClickListener {
            store.accept(Event.Ui.ClickNotifications)
        }

        val createUserButton = rootView?.findViewById<Button>(R.id.createUserButton)
        val createOfficeButton = rootView?.findViewById<Button>(R.id.createOfficeButton)
        createUserButton?.setOnClickListener {
            store.accept(Event.Ui.CLickCreateUser)
        }
        createOfficeButton?.setOnClickListener {
            store.accept(Event.Ui.ClickCreateOffice)
        }

        if (role == "ADMIN") {
            createUserButton?.visibility = View.VISIBLE
            createOfficeButton?.visibility = View.VISIBLE
        }

//        val requestsList = listOf(
//            Request(
//                Request.Destination((Request.Office(("Moscow")))),
//                "14.03.2000",
//                RequestStatus.PENDING,
//                "15.03.2001"
//            )
//        )
//        val tripsList = listOf(
//            Trip(
//                Trip.Destination((Trip.Office(("Moscow")))),
//                "14.03.2000",
//                TripStatus.CANCELLED,
//                "15.03.2001"
//            )
//        )
//
//
//        val requestsLayoutManager = LinearLayoutManager(this.context)
//        requestsLayoutManager.orientation = LinearLayoutManager.VERTICAL
//        requestRecycler?.layoutManager = requestsLayoutManager
//        requestRecycler?.adapter = HomeRequestsRecyclerAdapter(requestsList, rootView!!)
//
//        val tripsLayoutManager = LinearLayoutManager(this.context)
//        tripsLayoutManager.orientation = LinearLayoutManager.VERTICAL
//        tripRecycler?.layoutManager = tripsLayoutManager
//        tripRecycler?.adapter = HomeTripsRecyclerAdapter(tripsList, rootView!!)

        return rootView!!
    }

    override fun render(state: State) {
        val tripsLayoutManager = LinearLayoutManager(this.context)
        tripsLayoutManager.orientation = LinearLayoutManager.VERTICAL
        tripRecycler?.layoutManager = tripsLayoutManager
        tripRecycler?.adapter = rootView?.let { HomeTripsRecyclerAdapter(state.trips, it) }

        val requestsLayoutManager = LinearLayoutManager(this.context)
        requestsLayoutManager.orientation = LinearLayoutManager.VERTICAL
        requestRecycler?.layoutManager = requestsLayoutManager
        requestRecycler?.adapter = rootView?.let { HomeRequestsRecyclerAdapter(state.requests, it) }

        refreshLayout?.isRefreshing = state.loading
    }

    override fun handleEffect(effect: Effect) = when (effect) {
        Effect.ShowErrorNetwork -> Toast.makeText(
            activity,
            "Unexpected problems on the server",
            Toast.LENGTH_SHORT
        ).show()

        Effect.ShowLoadingError -> Toast.makeText(
            activity,
            "Problems with your Internet connection",
            Toast.LENGTH_SHORT
        ).show()

        Effect.ToNotificationActivity -> toNotificationActivity()
        Effect.ToRequestsActivity -> toRequestsActivity()
        Effect.ToTripsActivity -> toTripsActivity()
        Effect.ToCreateOfficeActivity -> toCreateOfficeActivity()
        Effect.ToCreateUserActivity -> toCreateUserActivity()
    }

    private fun toRequestsActivity() {
//        val intent = Intent(this.context, RequestsActivity::class.java)
//        startActivity(intent)
    }

    private fun toTripsActivity() {
//        val intent = Intent(this.context, TripsActivity::class.java)
//        startActivity(intent)
    }

    private fun toNotificationActivity() {
        val intent = Intent(this.context, NotificationActivity::class.java)
        startActivity(intent)
    }

    private fun toCreateOfficeActivity() {
//        val intent = Intent(this.context, CreateOfficeActivity::class.java)
//        startActivity(intent)
    }

    private fun toCreateUserActivity() {
        val intent = Intent(this.context, CreateUserActivity::class.java)
        startActivity(intent)
    }
}