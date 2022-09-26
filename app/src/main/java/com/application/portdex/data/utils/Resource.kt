package com.application.portdex.data.utils

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val error: Throwable? = null
) {
    class Success<T>(data: T) : Resource<T>(data)

    class Error<T>(message: String?, error: Throwable? = null) :
        Resource<T>(message = message, error = error)
}
