package com.application.portdex.presentation.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import com.application.portdex.R
import com.application.portdex.core.location.LocationPickerImpl
import com.application.portdex.core.utils.GenericUtils.getCountry
import com.application.portdex.core.utils.ValidationUtils
import com.application.portdex.data.mappers.toCreateProfile
import com.application.portdex.data.utils.Resource
import com.application.portdex.domain.viewmodels.ProfileViewModel
import com.application.portdex.presentation.base.BaseActivity
import com.jacopo.pagury.prefs.PrefUtils
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private val profileViewModel by viewModels<ProfileViewModel>()
    private val locationPicker = LocationPickerImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        if (ValidationUtils.isLoggedIn()) startMainActivity() else startWelcomeActivity()
//        locationPicker.initLocation(this) { info ->
//            if (ValidationUtils.isLoggedIn()) {
//                PrefUtils.getProfileInfo()?.toCreateProfile()?.let { profile ->
//                    profile.latitude = info.latitude.toString()
//                    profile.longitude = info.longitude.toString()
//                    profile.country = getCountry()
//
//                    profileViewModel.createProfile(profile, null) { resource ->
//                        when (resource) {
//                            is Resource.Success -> startMainActivity()
//                            is Resource.Error -> resource.message?.let { showToast(it) }
//                        }
//                    }
//                } ?: startWelcomeActivity()
//            } else startWelcomeActivity()
//        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPicker.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}