package com.example.businesstripsapp.main_activity.main_fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.auth0.android.jwt.JWT
import com.example.businesstripsapp.R
import com.example.businesstripsapp.main_activity.main_fragment.presentation.Effect
import com.example.businesstripsapp.main_activity.main_fragment.presentation.Event
import com.example.businesstripsapp.main_activity.main_fragment.presentation.State
import com.example.businesstripsapp.main_activity.main_fragment.presentation.storeFactory
import com.example.businesstripsapp.network.NetworkService
import com.example.businesstripsapp.trips_activity.TripsActivity
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.core.store.Store

class HomeFragment : ElmFragment<Event, Effect, State>() {
    private val token = NetworkService.instance.getToken()
    private val jwt: JWT = JWT(token)
    val id: String = jwt.getClaim("id").asString() ?: ""
    val role: String = jwt.getClaim("role").asString() ?: ""

    override fun createStore(): Store<Event, Effect, State> = storeFactory()
    override val initEvent: Event = Event.Ui.Init(id)

    private var requestRecycler : RecyclerView? = null
    private var tripRecycler : RecyclerView? = null
    private var rootView : View? = null
    private var refreshLayout: SwipeRefreshLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.fragment_main, null)
        requestRecycler = rootView?.findViewById(R.id.requestsRecycler)
        tripRecycler = rootView?.findViewById(R.id.tripsRecycler)
        refreshLayout = rootView?.findViewById(R.id.refreshLayout)

        refreshLayout?.setOnRefreshListener {
            store.accept(Event.Ui.PullToRefrech(id))
        }
        val tripsCard = rootView?.findViewById<CardView>(R.id.tripsCard)
        tripsCard?.setOnClickListener {
            store.accept(Event.Ui.ClickTrips)
        }
        val requestCard = rootView?.findViewById<CardView>(R.id.requestsCard)
        requestCard?.setOnClickListener {
            store.accept(Event.Ui.ClickRequests)
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
        Effect.ToNotificationActivity -> toNotificationsActivity()
        Effect.ToRequestsActivity -> toRequestsActivity()
        Effect.ToTripsActivity -> toTripsActivity()
    }

    private fun toRequestsActivity() {
//        val intent: Intent = Intent(this.context, RequestsActivity::class.java)
//        startActivity(intent)
    }

    private fun toTripsActivity() {
        val intent: Intent = Intent(this.context, TripsActivity::class.java)
        startActivity(intent)
    }

    private fun toNotificationsActivity() {
//        val intent: Intent = Intent(this.context, NotificationsActivity::class.java)
//        startActivity(intent)
    }
}