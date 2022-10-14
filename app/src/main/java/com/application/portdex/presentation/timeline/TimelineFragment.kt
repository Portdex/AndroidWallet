package com.application.portdex.presentation.timeline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.application.portdex.R
import com.application.portdex.databinding.FragmentTimelineBinding
import com.application.portdex.presentation.base.BaseFragment
import com.application.portdex.presentation.timeline.feed.FeedFragment
import com.google.android.material.tabs.TabLayoutMediator

class TimelineFragment : BaseFragment() {

    private lateinit var mBinding: FragmentTimelineBinding

    companion object {
        fun newInstance() = TimelineFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentTimelineBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPager()
    }

    private fun initPager() {
        val adapter = object : FragmentStateAdapter(this) {

            override fun createFragment(position: Int): Fragment {
                return FeedFragment.newInstance(position == 1)
            }

            override fun getItemCount() = 2
        }
        mBinding.viewPager.adapter = adapter
        TabLayoutMediator(mBinding.tabLayout, mBinding.viewPager) { tab, index ->
            tab.setText(
                when (index) {
                    0 -> R.string.label_business_feed
                    else -> R.string.label_my_feed
                }
            )
        }.attach()
    }
}