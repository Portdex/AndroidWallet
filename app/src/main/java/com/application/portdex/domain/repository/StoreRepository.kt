package com.application.portdex.domain.repository

import androidx.documentfile.provider.DocumentFile
import com.application.portdex.data.local.cart.CartEntity
import com.application.portdex.data.utils.Resource
import com.application.portdex.domain.models.ProviderPackage
import com.application.portdex.domain.models.store.StoreInfo
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

interface StoreRepository {

    fun uploadImage(file: DocumentFile): Single<String>
    fun createStore(store: StoreInfo, imageFile: DocumentFile? = null): Single<Resource<StoreInfo>>

    // cart
    fun insertIntoCart(item: ProviderPackage): Single<Long>
    fun deleteCartItem(item: ProviderPackage): Single<Int>
    fun getCartItems(): Flowable<List<CartEntity>>
    fun getCartItemsSimple(): Single<List<CartEntity>>
}