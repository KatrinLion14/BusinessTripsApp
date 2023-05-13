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
import androidx.recyclerview.widget.RecyclerView
import com.example.businesstripsapp.R
import com.example.businesstripsapp.requests_activity.requests_fragment.incoming_requests_fragment.models.Destination
import com.example.businesstripsapp.requests_activity.requests_fragment.incoming_requests_fragment.models.Office
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

    var officeArray: Array<Office> = arrayOf(
        Office("1234", "Пермь", "smth"),
        Office("1235", "Санкт-Петербург", "smth"),
        Office("1236", "Нижний Новгород", "smth"),
        Office("1237", "Владивосток", "smth"),
        Office("1238", "Самара", "smth")
    )

    var destinationArray: Array<Destination> = arrayOf(
        Destination("1234", "smth", officeArray[0], "4"),
        Destination("1235", "smth", officeArray[1], "5"),
        Destination("1236", "smth", officeArray[2], "6"),
        Destination("1237", "smth", officeArray[3], "7"),
        Destination("1238", "smth", officeArray[4], "8")
    )

    var requestsArray : Array<Request> = arrayOf(
        Request("4634636536", "Возвращена", "Петр Степанович", "Петров", destinationArray[0], "13.04.2023", "24.04.2023"),
        Request("3524523523", "Утверждена","Иван Иванович", "Иванов", destinationArray[1], "21.05.2023", "29.05.2023"),
        Request("3465363563","В обработке", "Степан Игоревич", "Степаненко", destinationArray[2], "07.07.2023", "20.07.2023"),
        Request("2534534534","Отклонена", "Игнат Васильевич", "Захаров", destinationArray[3], "16.07.2023", "24.07.2023"),
        Request("1123142345","Отклонена", "Никита Андреевич", "Кошкин", destinationArray[4], "17.08.2023", "26.08.2023")
    )

    private var requestsAdapter = IncomingRequestsAdapter(requestsArray, this)

    override val initEvent: Event = Event.Ui.Init //событие инициализации экрана

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_incoming_requests, container, false)
        requestsAdapter = IncomingRequestsAdapter(requestsArray, this)

        val token = activity?.intent?.getStringExtra("token") ?: ""
        val userId = getUserId(token)

        store.accept(
            Event.Ui.ShowRequests(userId)
        )

        R.layout.fragment_incoming_requests.apply {
            view?.findViewById<RecyclerView>(R.id.incoming_requests_recycler_view)?.adapter = requestsAdapter
        }

        return rootView
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
        is Effect.ShowAllRequests -> requestsArray = effect.requestsArray
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


}