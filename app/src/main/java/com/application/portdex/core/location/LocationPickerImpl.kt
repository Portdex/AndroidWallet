package com.application.portdex.core.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.application.portdex.R
import com.application.portdex.domain.models.LocationInfo
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.jacopo.pagury.prefs.PrefUtils
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest

class LocationPickerImpl : LocationPicker, LifecycleEventObserver,
    EasyPermissions.PermissionCallbacks {

    companion object {
        private const val TAG = "LocationPickerImpl"
        const val LOCATION_PERMISSION = 110
        const val GPS_PERMISSION = 111
    }

    private val permission = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private var listener: ((LocationInfo) -> Unit)? = null
    private var activity: FragmentActivity? = null
    private var locationProvider: FusedLocationProviderClient? = null
    private var getGpsResult: ActivityResultLauncher<IntentSenderRequest>? = null

    override fun initLocation(activity: FragmentActivity, listener: ((LocationInfo) -> Unit)?) {
        this.activity = activity
        this.listener = listener
        activity.lifecycle.addObserver(this)
        locationProvider = LocationServices.getFusedLocationProviderClient(activity)
        initLocation()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_CREATE) {
            try {
                getGpsResult = activity?.activityResultRegistry?.register(
                    "key", source, ActivityResultContracts.StartIntentSenderForResult()
                ) { result ->
                    if (result.resultCode == Activity.RESULT_OK) initLocation()
                }
            } catch (_: Exception) {
            }
        } else if (event == Lifecycle.Event.ON_PAUSE) {
            removeLocationRequest()
        }
    }

    @SuppressLint("MissingPermission")
    private fun initLocation() {
        val context = activity ?: return
        if (EasyPermissions.hasPermissions(context, *permission)) {
            locationProvider?.lastLocation?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    if (result?.latitude != null || result?.longitude != null) saveLocation(result)
                    else requestLocation()
                }
            }
        } else {
            EasyPermissions.requestPermissions(
                PermissionRequest.Builder(context, LOCATION_PERMISSION, *permission)
                    .setRationale(R.string.info_location_permission_required)
                    .setPositiveButtonText(R.string.button_ok)
                    .setNegativeButtonText(R.string.button_cancel)
                    .build()
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        val context = activity ?: return
        val manager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
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
        val locationInfo = LocationInfo(
            latitude = location.latitude,
            longitude = location.longitude
        )
        PrefUtils.saveLocation(locationInfo)
        listener?.invoke(locationInfo)
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

    private fun requestGps() {
        val context = activity ?: return
        val settingsRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(getLocationRequest())
            .setAlwaysShow(true)
            .build()
        LocationServices.getSettingsClient(context)
            .checkLocationSettings(settingsRequest)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        if (it is ApiException && it.statusCode == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                            try {
                                if (it is ResolvableApiException) {
                                    it.startResolutionForResult(context, GPS_PERMISSION)
                                    IntentSenderRequest.Builder(it.resolution.intentSender)
                                        .setFillInIntent(Intent())
                                        .build().also { request ->
                                            getGpsResult?.launch(request)
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

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.d(TAG, "onPermissionsGranted: $requestCode : ${perms.size}")
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Log.d(TAG, "onPermissionsDenied: $requestCode : ${perms.size}")
        activity?.showAppSettings(perms)
    }

    private fun FragmentActivity.showAppSettings(perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}