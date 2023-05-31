package com.example.businesstripsapp.requests_history_activity.requests_history_fragments.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(f: Fragment) :
    FragmentStateAdapter(f) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return OutgoingRequestsHistoryFragment()
            1 -> return IncomingRequestsHistoryFragment()
        }
        return OutgoingRequestsHistoryFragment()
    }

}