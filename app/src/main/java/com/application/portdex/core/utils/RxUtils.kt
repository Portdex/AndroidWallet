package com.application.portdex.core.utils

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

object RxUtils {

    fun <T : Any> Single<T>.request(): Single<T> {
        return subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun Completable.request(): Completable {
        return subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}