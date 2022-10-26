package com.application.portdex.presentation.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.application.portdex.CryptoScreens.Main2Activity
import com.application.portdex.R
import com.application.portdex.core.enums.HomeMenu
import com.application.portdex.databinding.ActivityMainBinding
import com.application.portdex.presentation.base.BaseActivity
import com.application.portdex.presentation.chat.ChatFragment
import com.application.portdex.presentation.home.HomeFragment
import com.application.portdex.presentation.timeline.TimelineFragment
import com.application.portdex.presentation.wallet.WalletFragment
import com.application.portdex.presentation.wallet.WalletFragmentNew
import com.google.android.material.navigation.NavigationBarView
import com.jacopo.pagury.prefs.PrefUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var mBinding: ActivityMainBinding


    private val homeFragment = HomeFragment.newInstance()
//    private val walletFragment = WalletFragmentNew.newInstance()
    private val timelineFragment = TimelineFragment.newInstance()
    private val chatFragment = ChatFragment.newInstance()
    private var activeFragment: Fragment = homeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        if (savedInstanceState == null) {
            initBottomNavigation()
        }
    }

    private fun initBottomNavigation() {
        showFragment(homeFragment, HomeMenu.Home)
        mBinding.bottomNavigation.selectedItemId = R.id.actionHome
        mBinding.bottomNavigation.setOnItemSelectedListener(this)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actionHome -> showFragment(homeFragment, HomeMenu.Home)
//            R.id.actionWallet -> showFragment(walletFragment, HomeMenu.Wallet)
            R.id.actionWallet -> showActivity(this, Main2Activity())
            R.id.actionTimeline -> showFragment(timelineFragment, HomeMenu.Timeline)
            R.id.actionChat -> if (authenticated()) showFragment(chatFragment, HomeMenu.Chat)
            else false
            else -> false
        }
    }

    fun showActivity (context : Context, activity: Activity) : Boolean{
        startActivity(Intent(context, activity::class.java))
        return true
    }
    private fun showFragment(fragment: Fragment, menu: HomeMenu): Boolean {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        val currentFragment = supportFragmentManager.primaryNavigationFragment


            if (currentFragment != null) {
                fragmentTransaction.hide(currentFragment)
                currentFragment.onPause()
                currentFragment.parentFragmentManager.fragments.forEach { child -> child.onPause() }
            }



        var fragmentTemp = supportFragmentManager.findFragmentByTag(menu.name)
        if (fragmentTemp == null) {
            fragmentTemp = fragment
            fragmentTransaction.add(R.id.main_container, fragmentTemp, menu.name)
        } else {
            fragmentTransaction.show(fragmentTemp)
            fragmentTemp.onResume()
            fragmentTemp.childFragmentManager.fragments.forEach { child -> child.onResume() }
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragmentTemp)
        fragmentTransaction.setReorderingAllowed(true)
        fragmentTransaction.commitNowAllowingStateLoss()
        activeFragment = fragment
        return true
    }

    override fun onBackPressed() {
        when {
            activeFragment != homeFragment -> {
                mBinding.bottomNavigation.selectedItemId = R.id.actionHome
            }
            else -> super.onBackPressed()
        }
    }
}