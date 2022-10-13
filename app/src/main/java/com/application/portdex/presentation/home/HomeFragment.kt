package com.application.portdex.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.application.portdex.R
import com.application.portdex.core.dataBinding.DataBinding.setUserImage
import com.application.portdex.databinding.FragmentHomeBinding
import com.application.portdex.presentation.base.BaseFragment
import com.application.portdex.presentation.dialogs.CreatePostSheet
import com.application.portdex.presentation.home.homeAll.HomeAllFragment
import com.application.portdex.presentation.home.homeMarket.HomeMarketFragment
import com.application.portdex.presentation.post.JobPostActivity
import com.application.portdex.presentation.profile.ProfileActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.jacopo.pagury.prefs.PrefUtils

class HomeFragment : BaseFragment() {

    private lateinit var mBinding: FragmentHomeBinding

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabs()

        mBinding.userImage.setOnClickListener {
            startWithAnim(Intent(requireContext(), ProfileActivity::class.java))
        }
        mBinding.actionAdd.setOnClickListener {
            val dialog = CreatePostSheet.newInstance()
            dialog.createJobPostListener = { startJobPost() }
            dialog.show(childFragmentManager, dialog.tag)
        }
    }

    private fun startJobPost() {
        startWithAnim(Intent(requireContext(), JobPostActivity::class.java))
    }

    override fun onResume() {
        super.onResume()
        PrefUtils.getProfileInfo()?.profilePicUrl?.let { mBinding.userImage.setUserImage(it) }
    }

    private fun initTabs() {
        val pagerAdapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> HomeAllFragment.newInstance()
                    else -> HomeMarketFragment.newInstance()
                }
            }

            override fun getItemCount() = 2
        }
        mBinding.viewPager.adapter = pagerAdapter
        mBinding.viewPager.isUserInputEnabled = false
        TabLayoutMediator(mBinding.tabLayout, mBinding.viewPager) { tab, index ->
            tab.setText(
                when (index) {
                    0 -> R.string.label_all
                    else -> R.string.label_digital_market
                }
            )
        }.attach()
    }

}