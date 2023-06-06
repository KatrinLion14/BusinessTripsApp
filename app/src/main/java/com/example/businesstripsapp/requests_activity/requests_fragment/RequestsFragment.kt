package com.example.businesstripsapp.requests_activity.requests_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.businesstripsapp.R
import com.example.businesstripsapp.requests_activity.requests_fragment.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class RequestsFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private val tabTitles: Array<String> = arrayOf(
        "Исходящие",
        "Входящие",
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_requests_container, container, false)
        tabLayout = view.findViewById(R.id.requests_tabs)
        viewPager = view.findViewById(R.id.viewpager)

        val pagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(
            tabLayout, viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = tabTitles[position]
        }.attach()

        return view
    }
}