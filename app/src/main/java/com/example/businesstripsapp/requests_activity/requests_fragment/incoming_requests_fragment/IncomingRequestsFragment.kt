package com.example.businesstripsapp.requests_activity.requests_fragment.incoming_requests_fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.businesstripsapp.R
import com.example.businesstripsapp.requests_activity.requests_fragment.incoming_requests_fragment.models.Request
import com.example.businesstripsapp.requests_activity.requests_fragment.incoming_requests_fragment.recycler_view_adapters.IncomingRequestsAdapter
import com.example.businesstripsapp.requests_activity.requests_fragment.incoming_requests_fragment.presentation.Effect
import com.example.businesstripsapp.requests_activity.requests_fragment.incoming_requests_fragment.presentation.Event
import com.example.businesstripsapp.requests_activity.requests_fragment.incoming_requests_fragment.presentation.State
import com.example.businesstripsapp.requests_history_activity.RequestsHistoryActivity
import org.json.JSONObject
import vivid.money.elmslie.android.base.ElmFragment
import java.util.Base64

class IncomingRequestsFragment : ElmFragment<Event, Effect, State>(R.layout.fragment_incoming_requests), IncomingRequestsAdapter.Listener {

    lateinit var requestsAdapter: IncomingRequestsAdapter
    override val initEvent: Event = Event.Ui.Init //событие инициализации экрана

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_incoming_requests, container, false)

        val token = activity?.intent?.getStringExtra("token") ?: ""
        val userId = getUserId(token)

        store.accept(
            Event.Ui.ShowRequests(userId)
        )

        return rootView
    }


    private fun initRecyclerView() {
        R.layout.fragment_incoming_requests.apply {
            val recyclerView : RecyclerView? = view?.findViewById(R.id.incoming_requests_recycler_view)
            recyclerView?.layoutManager = LinearLayoutManager(activity)
            recyclerView?.adapter = requestsAdapter
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getUserId(token: String) : String {
        val splitString = token.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        val base64EncodedBody = splitString[1]
        val body = String(Base64.getDecoder().decode(base64EncodedBody))
        val jsonObject = JSONObject(body)

        return jsonObject["id"].toString()
    }

    override fun render(state: State) {
        Log.i("STATE", "render state")
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
        intent.putExtra("requestType", "incoming")
        startActivity(intent)
    }

    private fun ShowRequests(requestsArray: Array<Request>) {
        requestsAdapter = IncomingRequestsAdapter(requestsArray, this)
        initRecyclerView()
    }
}