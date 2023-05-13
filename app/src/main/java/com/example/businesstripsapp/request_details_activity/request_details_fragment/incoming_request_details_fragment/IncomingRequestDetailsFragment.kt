package com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment

import com.example.businesstripsapp.R
import com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.presentation.Effect
import com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.presentation.Event
import com.example.businesstripsapp.request_details_activity.request_details_fragment.incoming_request_details_fragment.presentation.State
import vivid.money.elmslie.android.base.ElmFragment

class IncomingRequestDetailsFragment: ElmFragment<Event, Effect, State>(R.layout.fragment_incoming_request_details) {
    override val initEvent: Event
        get() = TODO("Not yet implemented")

    override fun render(state: State) {
        TODO("Not yet implemented")
    }

}