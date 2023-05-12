package com.example.businesstripsapp.main_activity.profile_fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.auth0.android.jwt.JWT
import com.example.businesstripsapp.R
import com.example.businesstripsapp.main_activity.domain.models.Request
import com.example.businesstripsapp.main_activity.domain.models.User
import com.example.businesstripsapp.main_activity.profile_fragment.presentation.Effect
import com.example.businesstripsapp.main_activity.profile_fragment.presentation.Event
import com.example.businesstripsapp.main_activity.profile_fragment.presentation.State
import com.example.businesstripsapp.main_activity.profile_fragment.presentation.storeFactory
import com.example.businesstripsapp.network.NetworkService
import com.example.businesstripsapp.notification_activity.NotificationsRecyclerAdapter
import com.example.businesstripsapp.notification_details_activity.NotificationDetailsActivity
import org.w3c.dom.Text
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.core.store.Store

class ProfileFragment : ElmFragment<Event, Effect, State>() {
    private val token = NetworkService.instance.getToken()
    private val jwt: JWT = JWT(token)
    val id: String = jwt.getClaim("id").asString() ?: ""

    override fun createStore(): Store<Event, Effect, State> = storeFactory()
    override val initEvent: Event = Event.Ui.Init(id)

    private var subordinateRecycler: RecyclerView? = null
    private var subordinatesCard: CardView? = null
    private var emailText: TextView? = null
    private var nameText: TextView? = null
    private var rootView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_profile, null)
        subordinateRecycler = rootView?.findViewById(R.id.subordinatesRecycler)
        subordinatesCard = rootView?.findViewById(R.id.subordinatesCard)
        emailText = rootView?.findViewById(R.id.profileEmail)
        nameText = rootView?.findViewById(R.id.username)

        val exitButton = rootView?.findViewById<Button>(R.id.exitButton)
        exitButton?.setOnClickListener {
            store.accept(Event.Ui.CLickExit)
        }
        return rootView
    }

    @SuppressLint("SetTextI18n")
    override fun render(state: State) {
        nameText?.text = "${state.user?.firstName} ${state.user?.secondName}"
        emailText?.text = state.user?.email
        if (state.user?.subordinates?.isNotEmpty() == true) {
            subordinatesCard?.visibility = View.VISIBLE
            val layoutManager = LinearLayoutManager(this.context)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            subordinateRecycler?.layoutManager = layoutManager
            subordinateRecycler?.adapter =
                ProfileSubordinatesRecyclerAdapter(state.user.subordinates, store)
        } else {
            subordinatesCard?.visibility = View.GONE
        }
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
        is Effect.ExitToStart -> {
            NetworkService.instance.destroyService()
            activity?.finish()
        }
        is Effect.ToSubordinateDetails -> toSubordinateDetails(effect.subordinate)
    }

    private fun toSubordinateDetails(subordinate: User) {
//        val intent = Intent(this, SubordinateDetailsActivity::class.java)
//        intent.putExtra("subordinate", subordinate)
//        startActivity(intent)
    }

}