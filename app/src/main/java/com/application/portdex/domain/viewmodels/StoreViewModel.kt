package com.application.portdex.domain.viewmodels

import android.util.Log
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModel
import com.application.portdex.core.utils.RxUtils.request
import com.application.portdex.data.mappers.toPackageList
import com.application.portdex.data.utils.Resource
import com.application.portdex.domain.models.ProviderPackage
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

    companion object {
        private const val TAG = "StoreViewModel"
    }

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

    fun insertIntoCart(item: ProviderPackage, listener: (Resource<Boolean>) -> Unit) {
        disposable.add(
            repository.insertIntoCart(item).request()
                .subscribeBy(onSuccess = { listener(Resource.Success(it > 0)) },
                    onError = { listener(Resource.Error(it.message)) })
        )
    }

    fun deleteCartItem(item: ProviderPackage, listener: (Resource<Boolean>) -> Unit) {
        disposable.add(
            repository.deleteCartItem(item).request()
                .subscribeBy(onSuccess = {
                    Log.d(TAG, "deleteCartItem: $it")
                    listener(Resource.Success(it > 0))
                }, onError = { listener(Resource.Error(it.message)) })
        )
    }

    fun getCartItems(listener: (Resource<List<ProviderPackage>>) -> Unit) {
        disposable.add(
            repository.getCartItems().request()
                .subscribeBy(onNext = { listener(Resource.Success(it.toPackageList())) },
                    onError = { listener(Resource.Error(it.message)) })
        )
    }

    fun getCartItemsSimple(listener: (Resource<List<ProviderPackage>>) -> Unit) {
        disposable.add(
            repository.getCartItemsSimple().request()
                .subscribeBy(onSuccess = { listener(Resource.Success(it.toPackageList())) },
                    onError = { listener(Resource.Error(it.message)) })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}