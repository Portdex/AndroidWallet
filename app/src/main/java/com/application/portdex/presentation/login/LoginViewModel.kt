package com.application.portdex.presentation.login

import androidx.lifecycle.ViewModel
import com.application.portdex.core.utils.RxUtils.request
import com.application.portdex.core.utils.ValidationUtils
import com.application.portdex.data.utils.Resource
import com.application.portdex.domain.repository.LoginRepository
import com.jacopo.pagury.prefs.PrefUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val password by lazy { ValidationUtils.generatePassword() }


    fun loginWithNumber(number: String, listener: (Resource<Boolean>) -> Unit) {
        disposable.add(
            repository.loginWithNumber(number, password).request()
                .subscribeBy(onSuccess = (listener), onError = {
                    listener(Resource.Error(it.message))
                })
        )
    }

    fun confirmLogin(code: String, listener: (Resource<Boolean>) -> Unit) {
        disposable.add(
            repository.confirmLogin(code).request()
                .subscribeBy(onSuccess = (listener), onError = {
                    listener(Resource.Error(it.message))
                })
        )
    }

    fun fetchAuthSession(listener: (Resource<Boolean>) -> Unit) {
        disposable.add(
            repository.fetchAuthSession().request()
                .subscribeBy(onSuccess = (listener), onError = {
                    listener(Resource.Error(it.message))
                })
        )
    }

    fun logOut(listener: (Resource<Boolean>) -> Unit) {
        repository.logOut().request().subscribeBy(onComplete = {
            PrefUtils.clear()
            listener(Resource.Success(true))
        }, onError = {
            listener(Resource.Error(it.message))
        })
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}