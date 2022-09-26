package com.application.portdex.domain.repository

import com.application.portdex.data.utils.Resource
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface LoginRepository {

    fun loginWithNumber(number: String, password: String): Single<Resource<Boolean>>
    fun signUpWithNumber(number: String): Single<Resource<Boolean>>
    fun confirmLogin(code: String): Single<Resource<Boolean>>
    fun fetchAuthSession(): Single<Resource<Boolean>>
    fun logOut(): Completable
}