package com.application.portdex.domain.viewmodels

import androidx.lifecycle.ViewModel
import com.application.portdex.core.utils.RxUtils.request
import com.application.portdex.data.utils.Resource
import com.application.portdex.domain.models.FeedItem
import com.application.portdex.domain.models.ProviderInfo
import com.application.portdex.domain.models.ProviderPackage
import com.application.portdex.domain.models.category.CategoryItem
import com.application.portdex.domain.repository.ServicesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val repository: ServicesRepository
) : ViewModel() {

    private val disposable = CompositeDisposable()

    fun getServicesProviders(listener: (Resource<List<ProviderInfo>>) -> Unit) {
        disposable.add(
            repository.getServicesProviders().request()
                .subscribeBy(onSuccess = (listener), onError = {
                    listener(Resource.Error(it.message))
                })
        )
    }

    fun getProfileByCategory(category: String, listener: (Resource<List<ProviderInfo>>) -> Unit) {
        disposable.add(
            repository.getProfileByCategory(category).request()
                .subscribeBy(onSuccess = (listener), onError = {
                    listener(Resource.Error(it.message))
                })
        )
    }

    fun getCategories(listener: (Resource<List<CategoryItem>>) -> Unit) {
        disposable.add(
            repository.getCategories().request()
                .subscribeBy(onSuccess = (listener), onError = {
                    listener(Resource.Error(it.message))
                })
        )
    }

    fun getNewsFeedPost(listener: (Resource<List<FeedItem>>) -> Unit) {
        disposable.add(
            repository.getNewsFeedPost().request()
                .subscribeBy(onSuccess = (listener), onError = {
                    listener(Resource.Error(it.message))
                })
        )
    }

    fun getProviderPackages(userId: String, listener: (Resource<List<ProviderPackage>>) -> Unit) {
        disposable.add(
            repository.getProviderPackages(userId).request()
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