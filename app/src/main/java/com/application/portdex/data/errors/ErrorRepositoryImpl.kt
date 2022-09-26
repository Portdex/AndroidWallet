package com.application.portdex.data.errors

import com.application.portdex.data.utils.Resource
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ErrorRepositoryImpl @Inject constructor() : ErrorRepository {

    override fun <T> getApiError(errors: List<Error>?): Resource.Error<T> {
        return Resource.Error(errors?.firstOrNull()?.message)
    }

    override fun <T> getError(type: ErrorEnum): Resource.Error<T> {
        val error = when (type) {
            ErrorEnum.noDataFound -> "No data found"
            ErrorEnum.updateFailed -> "Update failed, please try again later"
            ErrorEnum.loginFailed -> "Login failed"
        }
        return Resource.Error(error)
    }

    override fun <T : Any> getException(throwable: Throwable): Single<T> {
        return Single.error(throwable)
    }
}