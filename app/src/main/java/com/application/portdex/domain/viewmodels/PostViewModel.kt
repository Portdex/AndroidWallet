package com.application.portdex.domain.viewmodels

import androidx.lifecycle.ViewModel
import com.application.portdex.core.utils.RxUtils.request
import com.application.portdex.data.utils.Resource
import com.application.portdex.domain.models.post.CreatePost
import com.application.portdex.domain.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {

    private val disposable = CompositeDisposable()

    fun createPost(post: CreatePost, listener: (Resource<Boolean>) -> Unit) {
        disposable.add(
            repository.createPost(post).request()
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