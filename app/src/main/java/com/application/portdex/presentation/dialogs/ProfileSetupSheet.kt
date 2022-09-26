package com.application.portdex.presentation.dialogs

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.portdex.R
import com.application.portdex.core.utils.ValidationUtils.getValidString
import com.application.portdex.databinding.SheetProfileSetupBinding

class ProfileSetupSheet : BaseBottomSheet() {

    private lateinit var mBinding: SheetProfileSetupBinding
    var pickImageListener: (() -> Unit)? = null
    var onContinueListener: ((Uri?, String, Boolean) -> Unit)? = null
    private var imageUrl: Uri? = null

    companion object {
        fun newInstance() = ProfileSetupSheet()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        mBinding = SheetProfileSetupBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.imageContainer.setOnClickListener { pickImageListener?.invoke() }
        mBinding.actionIcon.setOnClickListener { pickImageListener?.invoke() }
        mBinding.btnContinue.setOnClickListener { onContinue(false) }
        mBinding.btnBusiness.setOnClickListener { onContinue(true) }

    }

    private fun onContinue(isBusiness: Boolean) {
        val name = mBinding.inputName.getValidString()
        if (name.isNullOrEmpty()) {
            mBinding.inputNameLayout.apply {
                error = getString(R.string.error_input_required)
                requestFocus()
            }
        } else {
            onContinueListener?.invoke(imageUrl, name, isBusiness)
            dismiss()
        }
    }

    fun setImage(uri: Uri) {
        imageUrl = uri
        mBinding.imageView.setImageURI(uri)
    }
}