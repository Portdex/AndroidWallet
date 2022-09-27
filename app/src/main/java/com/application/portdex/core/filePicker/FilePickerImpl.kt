package com.application.portdex.core.filePicker

import android.Manifest
import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.application.portdex.R
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest

class FilePickerImpl : FilePicker, LifecycleEventObserver,
    EasyPermissions.PermissionCallbacks {


    companion object {
        private const val TAG = "FilePickerImpl"
        const val GALLERY_PERMISSION = 13
    }

    private var activity: FragmentActivity? = null
    private var getImage: ActivityResultLauncher<String>? = null
    private var pickImageListener: ((Uri) -> Unit)? = null

    override fun setPickImageListener(listener: (Uri) -> Unit) {
        this.pickImageListener = listener
    }

    override fun initPicker(activity: FragmentActivity) {
        this.activity = activity
        activity.lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_CREATE) {
            getImage = activity?.activityResultRegistry?.register(
                "key", source, ActivityResultContracts.GetContent()
            ) { uri -> uri?.let { pickImageListener?.invoke(it) } }
        }
    }

    @AfterPermissionGranted(GALLERY_PERMISSION)
    override fun pickImage() {
        if (hasStoragePermission()) getImage?.launch("image/*")
        else activity?.requestStoragePermission()
    }

    private fun FragmentActivity.requestStoragePermission() {
        EasyPermissions.requestPermissions(
            PermissionRequest.Builder(
                this, GALLERY_PERMISSION, Manifest.permission.READ_EXTERNAL_STORAGE
            ).setRationale(R.string.info_storage_permission_required)
                .setPositiveButtonText(R.string.button_ok)
                .setNegativeButtonText(R.string.button_cancel)
                .build()
        )
    }

    private fun hasStoragePermission(): Boolean {
        return activity?.let {
            EasyPermissions.hasPermissions(it, Manifest.permission.READ_EXTERNAL_STORAGE)
        } ?: false
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