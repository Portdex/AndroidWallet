package com.application.portdex.domain.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.application.portdex.core.utils.RxUtils.request
import com.application.portdex.domain.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: ChatRepository
) : ViewModel() {

    companion object {
        private const val TAG = "ChatViewModel"
    }

    fun connect() {
        repository.connect().request()
            .subscribeBy(onSuccess = {
                Log.d(TAG, "connect: $it")
            }, onError = {
                Log.e(TAG, "connect: ", it)
            })
    }
}