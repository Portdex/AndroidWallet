package com.application.portdex.presentation.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.View
import com.application.portdex.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


abstract class BaseBottomSheet : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_BottomSheet)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setOnShowListener {
                val dialog = it as BottomSheetDialog
                val bottomSheet =
                    dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                bottomSheet?.let { sheet ->
                    dialog.behavior.apply {
                        state = BottomSheetBehavior.STATE_EXPANDED
                        skipCollapsed = true
                    }
                    sheet.setBackgroundResource(android.R.color.transparent)
                    sheet.parent.parent.requestLayout()
                }
//                dialog.window?.apply {
//                    addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//                    navigationBarColor = Color.BLACK
//                }
            }
        }
    }

}