package com.application.portdex.data.errors

import com.application.portdex.data.utils.Resource
import io.reactivex.rxjava3.core.Single

interface ErrorRepository {

    fun <T> getError(type: ErrorEnum): Resource.Error<T>

    fun <T> getApiError(errors: List<Error>?): Resource.Error<T>

    fun <T : Any> getException(throwable: Throwable): Single<T>
}