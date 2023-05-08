package com.example.businesstripsapp.requests_activity.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.businesstripsapp.R
import com.example.businesstripsapp.requests_activity.recycler_view_adapters.OutgoingRequestsAdapter
import com.example.businesstripsapp.requests_activity.models.Request
import com.example.businesstripsapp.requests_activity.presentation.Effect
import com.example.businesstripsapp.requests_activity.presentation.Event
import com.example.businesstripsapp.requests_activity.presentation.State
import com.example.businesstripsapp.requests_history_activity.RequestsHistoryActivity
import vivid.money.elmslie.android.base.ElmFragment

class OutgoingRequestsFragment : ElmFragment<Event, Effect, State>(R.layout.fragment_outgoing_requests), OutgoingRequestsAdapter.Listener {

    var requestsArray : Array<Request> = arrayOf(
        Request("1023325457", "","В обработке", "Москва", "15.03.2023"),
        Request("4634636536", "","Возвращена", "Пермь", "13.04.2023"),
        Request("3524523523","", "Утверждена", "Санкт-Петербург", "21.05.2023"),
        Request("3465363563", "","В обработке", "Нижний Новгород", "07.07.2023"),
        Request("2534534534", "","Отклонена", "Владивосток", "16.07.2023"),
        Request("1123142345", "","Отклонена", "Самара", "17.08.2023")
    )

    private var requestsAdapter = OutgoingRequestsAdapter(requestsArray, this)

    override val initEvent: Event = Event.Ui.Init //событие инициализации экрана

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        requestsAdapter = OutgoingRequestsAdapter(requestsArray, this)
        return inflater.inflate(R.layout.fragment_outgoing_requests, container, false)
    }

    override fun render(state: State) {
        Log.i("STATE", "render state")
    }

    override fun handleEffect(effect: Effect) = when (effect) {  //обрабатывает side Effect
        is Effect.ShowError -> Toast.makeText(applicationContext, "Can not show the requests", Toast.LENGTH_SHORT).show()
        is Effect.ToRequestDetailsActivity -> toRequestDetailsActivity(effect.requestId)
        is Effect.ShowAllRequests ->
    }

    override fun onClick(request: Request) {
        store.accept(
            Event.Ui.OnRequestClicked(request.requestId)
        )
    }

    private fun toRequestDetailsActivity(requestId: String) {
        val intent: Intent = Intent(getActivity().this, RequestsHistoryActivity::class.java)
        intent.putExtra("requestId", requestId)
        startActivity(intent)
    }


}