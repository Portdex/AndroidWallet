package com.application.portdex.presentation.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.portdex.databinding.ProfileSelectionSheetBinding

class ProfileSelectionSheet : BaseBottomSheet() {

    private lateinit var mBinding: ProfileSelectionSheetBinding
    private var isBusiness = false
    var onProfileSelection: ((Boolean) -> Unit)? = null

    companion object {
        fun newInstance() = ProfileSelectionSheet()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = ProfileSelectionSheetBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.actionBack.setOnClickListener { dismiss() }
        mBinding.btnCreate.setOnClickListener {
            onProfileSelection?.invoke(isBusiness)
            dismiss()
        }
        mBinding.btnYes.setOnClickListener {
            isBusiness = true
            updateToggle()
        }
        mBinding.btnNo.setOnClickListener {
            isBusiness = false
            updateToggle()
        }
        updateToggle()
    }

    private fun updateToggle() {
        mBinding.toggleBusiness.isActivated = isBusiness
        mBinding.toggleNormal.isActivated = !isBusiness
    }
}