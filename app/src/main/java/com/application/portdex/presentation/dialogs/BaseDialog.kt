package com.application.portdex.presentation.dialogs

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.application.portdex.R

abstract class BaseDialog : DialogFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogFullScreen)
    }

    override fun onStart() {
        super.onStart()
        dialog?.let { dialog ->
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
        }
    }

}