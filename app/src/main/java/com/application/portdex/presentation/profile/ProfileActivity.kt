package com.application.portdex.presentation.profile

import android.os.Bundle
import androidx.activity.viewModels
import com.application.portdex.R
import com.application.portdex.adapters.ProfileItemAdapter
import com.application.portdex.core.dataBinding.DataBinding.setUserImage
import com.application.portdex.core.prefs.ActivityPreference
import com.application.portdex.core.prefs.NotifyPreference
import com.application.portdex.core.utils.GenericUtils.show
import com.application.portdex.core.utils.ValidationUtils
import com.application.portdex.core.utils.ValidationUtils.isSuccess
import com.application.portdex.data.local.LocalCategories.getProfilePaymentList
import com.application.portdex.data.local.LocalCategories.getProfileRewards
import com.application.portdex.data.utils.Resource
import com.application.portdex.databinding.ProfileActivityBinding
import com.application.portdex.domain.models.SimpleItem
import com.application.portdex.domain.viewmodels.ChatViewModel
import com.application.portdex.domain.viewmodels.ProfileViewModel
import com.application.portdex.presentation.base.BaseActivity
import com.application.portdex.presentation.login.LoginViewModel
import com.application.portdex.ui.CustomUi.getGridLabeledView
import com.application.portdex.ui.CustomUi.getViewAdapter
import com.jacopo.pagury.prefs.PrefUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : BaseActivity() {

    private val chatViewModel by viewModels<ChatViewModel>()
    private val loginViewModel by viewModels<LoginViewModel>()
    private val profileViewModel by viewModels<ProfileViewModel>()
    private lateinit var mBinding: ProfileActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ProfileActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        initData()
    }

    private fun initData() {
        mBinding.btnLogout.show(ValidationUtils.isLoggedIn())
        addSection(getString(R.string.label_bill_payments), getProfilePaymentList())
        addSection(getString(R.string.label_rewards), getProfileRewards())

        initViews()
        PrefUtils.getLoginInfo()?.number?.let { number ->
            profileViewModel.getUserProfile(number) { resource ->
                when (resource) {
                    is Resource.Success -> resource.data?.let {
                        PrefUtils.setProfileInfo(it)
                        initViews()
                    }
                    is Resource.Error -> resource.message?.let { showToast(it) }
                }
            }
        } ?: mBinding.userName.setText(R.string.label_guest_user)
        mBinding.btnLogout.setOnClickListener {
            showProgress()
            loginViewModel.logOut { resource ->
                hideProgress()
                when (resource) {
                    is Resource.Success -> if (resource.data.isSuccess()) {
                        ActivityPreference.clearPreferences(this)
                        NotifyPreference.clearPreferences(this)
                        chatViewModel.cleanChat()
                        startWelcomeActivity()
                    }
                    is Resource.Error -> resource.message?.let { showToast(it) }
                }
            }
        }
    }

    private fun initViews() {
        PrefUtils.getProfileInfo()?.let { profile ->
            var name = profile.firstName
            if (profile.lastName != "undefined") name += " ${profile.lastName}"
            mBinding.userName.text = name
            profile.profilePicUrl?.let { mBinding.userImage.setUserImage(it) }
        }
    }

    private fun addSection(label: String, list: MutableList<SimpleItem>) {
        mBinding.mainContainer.getViewAdapter(label)?.let { adapter ->
            if (adapter is ProfileItemAdapter) adapter.addList(list)
        } ?: mBinding.mainContainer.addView(getGridLabeledView(label, 4).apply {
            recyclerView.adapter = ProfileItemAdapter().apply {
                addList(list)
            }
        }.root)
    }
}