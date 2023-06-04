package com.example.businesstripsapp.requests_history_activity.requests_history_fragments.outgoing_requests_history_fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.auth0.android.jwt.JWT
import com.example.businesstripsapp.R
import com.example.businesstripsapp.network.NetworkService
import com.example.businesstripsapp.requests_history_activity.RequestsHistoryActivity
import com.example.businesstripsapp.requests_history_activity.requests_history_fragments.outgoing_requests_history_fragment.domain.models.Request
import com.example.businesstripsapp.requests_history_activity.requests_history_fragments.outgoing_requests_history_fragment.presentation.Effect
import com.example.businesstripsapp.requests_history_activity.requests_history_fragments.outgoing_requests_history_fragment.presentation.Event
import com.example.businesstripsapp.requests_history_activity.requests_history_fragments.outgoing_requests_history_fragment.presentation.State
import com.example.businesstripsapp.requests_history_activity.requests_history_fragments.outgoing_requests_history_fragment.recycler_view_adapters.OutgoingRequestsHistoryAdapter
import vivid.money.elmslie.android.base.ElmFragment

class OutgoingRequestsHistoryFragment : ElmFragment<Event, Effect, State>(R.layout.fragment_outgoing_requests_history), OutgoingRequestsHistoryAdapter.Listener {
    lateinit var requestsAdapter: OutgoingRequestsHistoryAdapter
    override val initEvent: Event = Event.Ui.Init //событие инициализации экрана

    private val token = NetworkService.instance.getToken()
    private val jwt: JWT = JWT(token)
    private val role: String = jwt.getClaim("role").asString() ?: ""
    val userId: String = jwt.getClaim("id").asString() ?: ""

    private var progressBar: RelativeLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_outgoing_requests, container, false)

        progressBar = view?.findViewById(R.id.progressBar)

        store.accept(
            Event.Ui.ShowRequests(userId)
        )

        return rootView
    }

    private fun ShowRequests(requestsArray: Array<Request>) {
        requestsAdapter = OutgoingRequestsHistoryAdapter(requestsArray, this)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        R.layout.fragment_outgoing_requests.apply {
            val recyclerView : RecyclerView? = view?.findViewById(R.id.outgoing_requests_recycler_view)
            recyclerView?.layoutManager = LinearLayoutManager(activity)
            recyclerView?.adapter = requestsAdapter
        }
    }

    override fun render(state: State) {
        if (state.isLoading) {
            progressBar?.visibility = View.VISIBLE
        } else {
            progressBar?.visibility = View.INVISIBLE
        }
    }

    override fun handleEffect(effect: Effect) = when (effect) {  //обрабатывает side Effect
        is Effect.ShowError -> Toast.makeText(activity, "Can not show the requests", Toast.LENGTH_SHORT).show()
        is Effect.ToRequestDetailsActivity -> toRequestDetailsActivity(effect.requestId)
        is Effect.ShowAllRequests -> ShowRequests(effect.requestsArray)
    }

    override fun onClick(request: Request) {
        store.accept(
            Event.Ui.OnRequestClicked(request.id)
        )
    }

    private fun toRequestDetailsActivity(requestId: String) {
        val intent: Intent = Intent(activity, RequestsHistoryActivity::class.java)
        intent.putExtra("requestId", requestId)
        intent.putExtra("requestType", "outgoing")
        startActivity(intent)
    }

}