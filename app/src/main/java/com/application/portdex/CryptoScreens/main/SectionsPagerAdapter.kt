package com.application.portdex.CryptoScreens.ui.main


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.application.portdex.CryptoScreens.Fragments.CreatePayment
import com.application.portdex.CryptoScreens.ui.Fragments.NFTFragment
import com.application.portdex.Fragments.CryptoFragment

class SectionsPagerAdapter(
    private val totalTabs: Int = 0,
    fm: FragmentManager
) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int {
        return totalTabs
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                CryptoFragment()

            }
            1 -> {
                NFTFragment()
            }

            2 ->{
               CreatePayment()
            }
            else ->  CryptoFragment()
        }
    }
}
