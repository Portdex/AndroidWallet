package com.application.portdex.CryptoScreens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.application.portdex.CryptoScreens.ui.main.SectionsPagerAdapter
import com.application.portdex.R
import com.application.portdex.databinding.ActivityMain2Binding
import com.application.portdex.presentation.base.BaseFragment
import com.google.android.material.tabs.TabLayout

class Main2Activity : AppCompatActivity()  {

    private lateinit var binding: ActivityMain2Binding

//    companion object {
//        fun newInstance() = Main2Activity()
//    }


//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = ActivityMain2Binding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        setTabs()
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

//        val sectionsPagerAdapter = SectionsPagerAdapter(2, supportFragmentManager)
//        val viewPager: ViewPager = binding.viewPager
//        viewPager.adapter = sectionsPagerAdapter
//        val tabs: TabLayout = binding.tabs
//        tabs.setupWithViewPager(viewPager)

        setTabs()

    }

    private fun setTabs() {
        binding.tabs.addTab(binding.tabs.newTab().setText(getString(R.string.crypto)))
        binding.tabs.addTab(binding.tabs.newTab().setText(getString(R.string.nft)))
        binding.tabs.addTab(binding.tabs.newTab().setText(getString(R.string.payment)))
        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewPager.currentItem = tab!!.position

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                binding.viewPager.currentItem = tab!!.position

            }

        });

        val numberOfTabs = 3
        binding.tabs.tabMode = TabLayout.MODE_SCROLLABLE
        binding.tabs.isTabIndicatorFullWidth = true;

        val adapter = SectionsPagerAdapter(numberOfTabs, this.supportFragmentManager)
        binding.viewPager.adapter = adapter
        binding.viewPager.currentItem = 0
//        vp_ride_pager.offscreenPageLimit = 3

        binding.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabs))
    }


}