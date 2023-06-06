package com.example.businesstripsapp.requests_activity.requests_fragment.adapters

import com.example.businesstripsapp.requests_activity.requests_fragment.incoming_requests_fragment.IncomingRequestsFragment
import com.example.businesstripsapp.requests_activity.requests_fragment.outgoing_requests_fragment.OutgoingRequestsFragment
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(f: Fragment) :
    FragmentStateAdapter(f) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return OutgoingRequestsFragment()
            1 -> return IncomingRequestsFragment()
        }
        return OutgoingRequestsFragment()
    }

}