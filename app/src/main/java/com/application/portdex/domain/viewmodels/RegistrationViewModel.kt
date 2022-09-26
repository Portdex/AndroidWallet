package com.application.portdex.domain.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.application.portdex.core.utils.RxUtils.request
import com.application.portdex.domain.models.CreateProfileInfo
import com.application.portdex.domain.repository.RegistrationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: RegistrationRepository
) : ViewModel() {

    companion object {
        private const val TAG = "RegistrationViewModel"
    }

    fun createProfile(profile: CreateProfileInfo) {
        repository.createProfile(profile).request()
            .subscribeBy(onSuccess = {
                Log.d(TAG, "createProfile: success")
            }, onError = {
                Log.e(TAG, "createProfile: ", it)
            })
    }
}