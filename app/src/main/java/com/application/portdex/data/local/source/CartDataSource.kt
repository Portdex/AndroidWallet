package com.application.portdex.data.local.source

import com.application.portdex.data.local.cart.CartEntity
import com.application.portdex.data.local.dao.CartDao
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CartDataSource @Inject constructor(
    private val cartDao: CartDao
) {

    fun insert(cart: CartEntity): Single<Long> {
        return cartDao.insert(cart)
    }

    fun delete(cart: CartEntity): Single<Int> {
        return cartDao.delete(cart.packageId)
    }

    fun getItems(): Flowable<List<CartEntity>> {
        return cartDao.getItems()
    }

    fun getItemsSimple(): Single<List<CartEntity>> {
        return cartDao.getItemsSimple()
    }
}