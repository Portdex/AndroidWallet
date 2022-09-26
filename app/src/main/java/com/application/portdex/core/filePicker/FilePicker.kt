package com.application.portdex.core.filePicker

import android.net.Uri
import androidx.fragment.app.FragmentActivity

interface FilePicker {

    fun initPicker(activity: FragmentActivity)
    fun pickImage()

    fun setPickImageListener(listener: (Uri) -> Unit)

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        receiver: FragmentActivity
    )
}