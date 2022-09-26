package com.application.portdex.domain.viewmodels

import androidx.lifecycle.ViewModel
import com.application.portdex.core.utils.RxUtils.request
import com.application.portdex.data.utils.Resource
import com.application.portdex.domain.models.ProfileInfo
import com.application.portdex.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {

    private val disposable = CompositeDisposable()

    fun getUserProfile(number: String, listener: (Resource<ProfileInfo>) -> Unit) {
        disposable.add(
            repository.getUserProfile(number).request()
                .subscribeBy(onSuccess = (listener), onError = {
                    listener(Resource.Error(it.message))
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}