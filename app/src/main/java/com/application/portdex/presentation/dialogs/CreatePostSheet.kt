package com.application.portdex.presentation.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.portdex.databinding.CreatePostSheetBinding

class CreatePostSheet : BaseBottomSheet() {

    private lateinit var mBinding: CreatePostSheetBinding
    var createJobPostListener: (() -> Unit)? = null

    companion object {
        fun newInstance() = CreatePostSheet()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = CreatePostSheetBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.actionClose.setOnClickListener { dismiss() }
        mBinding.actionPost.setOnClickListener {
            createJobPostListener?.invoke()
            dismiss()
        }
    }

}