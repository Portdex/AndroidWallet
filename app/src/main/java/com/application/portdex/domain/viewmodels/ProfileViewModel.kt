package com.application.portdex.domain.viewmodels

import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModel
import com.application.portdex.core.utils.RxUtils.request
import com.application.portdex.data.utils.Resource
import com.application.portdex.domain.models.CreateProfileInfo
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

    companion object {
        private const val TAG = "ProfileViewModel"
    }

    private val disposable = CompositeDisposable()

    fun getUserProfile(number: String, listener: (Resource<ProfileInfo>) -> Unit) {
        disposable.add(
            repository.getUserProfile(number).request()
                .subscribeBy(onSuccess = (listener), onError = {
                    listener(Resource.Error(it.message))
                })
        )
    }

    fun createProfile(
        profile: CreateProfileInfo,
        imageFile: DocumentFile?,
        listener: (Resource<Boolean>) -> Unit
    ) {
        disposable.add(
            repository.createProfile(profile, imageFile).request()
                .subscribeBy(onSuccess = (listener), onError = {
                    listener(Resource.Error(it.message))
                })
        )
    }

    fun getNearByUsers(listener: (Resource<List<ProfileInfo>>) -> Unit) {
        disposable.add(
            repository.getNearByUsers().request()
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