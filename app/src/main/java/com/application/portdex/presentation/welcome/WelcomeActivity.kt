package com.application.portdex.presentation.welcome

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.application.portdex.R
import com.application.portdex.databinding.WelcomeActivityBinding
import com.application.portdex.domain.models.LocationInfo
import com.application.portdex.presentation.base.BaseActivity
import com.application.portdex.presentation.login.LoginActivity
import com.application.portdex.presentation.main.MainActivity
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.jacopo.pagury.prefs.PrefUtils
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest

class WelcomeActivity : BaseActivity() {

    companion object {
        const val LOCATION_PERMISSION = 110
        const val GPS_PERMISSION = 111
    }

    private lateinit var mBinding: WelcomeActivityBinding

    private val permission = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    private var locationProvider: FusedLocationProviderClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = WelcomeActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        locationProvider = LocationServices.getFusedLocationProviderClient(this)
        mBinding.btnLogin.setOnClickListener {
            startWithAnim(Intent(this, LoginActivity::class.java))
        }
        mBinding.btnGuest.setOnClickListener {
            startWithAnim(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        initLocation()
    }

    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(LOCATION_PERMISSION)
    private fun initLocation() {
        if (EasyPermissions.hasPermissions(this, *permission)) {
            locationProvider?.lastLocation?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    if (result?.latitude != null || result?.longitude != null) saveLocation(result)
                    else requestLocation()
                }
            }
        } else {
            EasyPermissions.requestPermissions(
                PermissionRequest.Builder(this, LOCATION_PERMISSION, *permission)
                    .setRationale(R.string.info_location_permission_required)
                    .setPositiveButtonText(R.string.button_ok)
                    .setNegativeButtonText(R.string.button_cancel)
                    .build()
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        val manager = getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        if (manager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == false) {
            Log.d(TAG, "requestLocation: gps is disabled")
            requestGps()
        } else {
            locationProvider?.requestLocationUpdates(
                getLocationRequest(), locationCallback, Looper.myLooper()
            )
        }
    }

    private fun saveLocation(location: Location) {
        Log.d(TAG, "saveLocation: ${location.latitude} : ${location.longitude}")
        PrefUtils.saveLocation(
            LocationInfo(latitude = location.latitude, longitude = location.longitude)
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.locations.firstOrNull()?.let { location ->
                removeLocationRequest()
                saveLocation(location)
            }
        }
    }

    private fun getLocationRequest(): LocationRequest {
        return LocationRequest.create().apply {
            interval = 100
            fastestInterval = 50
            priority = Priority.PRIORITY_HIGH_ACCURACY
            maxWaitTime = 100
        }
    }

    private val resolutionResult =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) initLocation()
        }

    private fun requestGps() {
        val settingsRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(getLocationRequest())
            .setAlwaysShow(true)
            .build()
        LocationServices.getSettingsClient(this)
            .checkLocationSettings(settingsRequest)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        if (it is ApiException && it.statusCode == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                            try {
                                if (it is ResolvableApiException) {
                                    it.startResolutionForResult(this, GPS_PERMISSION)
                                    IntentSenderRequest.Builder(it.resolution.intentSender)
                                        .setFillInIntent(Intent())
                                        .build().also { request ->
                                            resolutionResult.launch(request)
                                        }
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
                }
            }
    }

    private fun removeLocationRequest() {
        locationProvider?.removeLocationUpdates(locationCallback)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPause() {
        super.onPause()
        removeLocationRequest()
    }
}