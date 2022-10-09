package com.application.portdex.domain.viewmodels

import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModel
import com.application.portdex.core.utils.RxUtils.request
import com.application.portdex.data.utils.Resource
import com.application.portdex.domain.models.store.StoreInfo
import com.application.portdex.domain.repository.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val repository: StoreRepository
) : ViewModel() {

    private val disposable = CompositeDisposable()

    fun createStore(
        store: StoreInfo,
        imageFile: DocumentFile?,
        listener: (Resource<StoreInfo>) -> Unit
    ) {
        disposable.add(
            repository.createStore(store, imageFile).request()
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