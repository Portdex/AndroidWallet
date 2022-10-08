package com.application.portdex.presentation.main

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.application.portdex.R
import com.application.portdex.core.enums.HomeMenu
import com.application.portdex.core.location.LocationPickerImpl
import com.application.portdex.core.utils.ValidationUtils
import com.application.portdex.data.remote.xmpptcp.service.XMPPServiceImpl
import com.application.portdex.databinding.ActivityMainBinding
import com.application.portdex.presentation.base.BaseActivity
import com.application.portdex.presentation.chat.ChatFragment
import com.application.portdex.presentation.home.HomeFragment
import com.application.portdex.presentation.timeline.TimelineFragment
import com.application.portdex.presentation.wallet.WalletFragment
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var mBinding: ActivityMainBinding
    private val locationPicker = LocationPickerImpl()

    private val homeFragment = HomeFragment.newInstance()
    private val walletFragment = WalletFragment.newInstance()
    private val timelineFragment = TimelineFragment.newInstance()
    private val chatFragment = ChatFragment.newInstance()
    private var activeFragment: Fragment = homeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        locationPicker.initLocation(this)
        if (ValidationUtils.isLoggedIn()) startChatService()
        if (savedInstanceState == null) {
            initBottomNavigation()
        }

//        startChatActivity(Bundle().apply {
//            putParcelable(
//                ChatActivity.PROFILE_ITEM, ProfileInfo(
//                    userId = "dadb5341-6ac2-4f69-88a5-8bdf901a1057",
//                    firstName = "Ali Raza",
//                    profilePicUrl = "https://s3.eu-west-2.amazonaws.com/my-portdex-data-demo/B1489375-AFBB-4983-91D4-EC68502142F1-718-000000098576A24C.jpg"
//                )
//            )
//        })
    }

    private fun startChatService() {
        val intent = Intent(this, XMPPServiceImpl::class.java)
        startService(intent)
        bindChatService()
    }

    private fun bindChatService() {
        val intent = Intent(this, XMPPServiceImpl::class.java)
        bindService(intent, chatConnection(), Context.BIND_AUTO_CREATE)
    }

    private fun chatConnection() = object : ServiceConnection {

        override fun onServiceConnected(componentName: ComponentName, binder: IBinder) {
            Log.d(TAG, "onServiceConnected: ")
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            Log.d(TAG, "onServiceDisconnected: ")
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
            R.id.actionWallet -> showFragment(walletFragment, HomeMenu.Wallet)
            R.id.actionTimeline -> showFragment(timelineFragment, HomeMenu.Timeline)
            R.id.actionChat -> if (authenticated()) showFragment(chatFragment, HomeMenu.Chat)
            else false
            else -> false
        }
    }

    private fun showFragment(fragment: Fragment, menu: HomeMenu): Boolean {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        val currentFragment = supportFragmentManager.primaryNavigationFragment
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment)
            currentFragment.onPause()
            currentFragment.childFragmentManager.fragments.forEach { child -> child.onPause() }
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPicker.onRequestPermissionsResult(requestCode, permissions, grantResults)
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