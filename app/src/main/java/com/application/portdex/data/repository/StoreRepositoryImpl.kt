package com.application.portdex.data.repository

import android.util.Log
import androidx.documentfile.provider.DocumentFile
import com.application.portdex.core.utils.RxUtils.request
import com.application.portdex.data.errors.ErrorRepository
import com.application.portdex.data.local.cart.CartEntity
import com.application.portdex.data.local.source.CartDataSource
import com.application.portdex.data.mappers.toCartItem
import com.application.portdex.data.remote.ApiEndPoints
import com.application.portdex.data.remote.ApiService
import com.application.portdex.data.utils.Resource
import com.application.portdex.domain.models.ProviderPackage
import com.application.portdex.domain.models.store.StoreInfo
import com.application.portdex.domain.repository.StorageRepository
import com.application.portdex.domain.repository.StoreRepository
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val storage: StorageRepository,
    private val cartDataSource: CartDataSource,
    private val error: ErrorRepository
) : StoreRepository {

    companion object {
        private const val TAG = "StoreRepositoryImpl"
    }

    override fun uploadImage(file: DocumentFile): Single<String> {
        return storage.uploadImage(file)
    }

    override fun createStore(
        store: StoreInfo, imageFile: DocumentFile?
    ): Single<Resource<StoreInfo>> {
        return imageFile?.let { file ->
            uploadImage(file).onErrorResumeNext { error.getException(it) }.flatMap { imageUrl ->
                store.storePicUrl = imageUrl
                apiService.saveStore(ApiEndPoints.getSaveStore(), store)
                    .onErrorResumeNext { error.getException(it) }
                    .map { response ->
                        Resource.Success(store.apply {
                            storeId = response.storeId
                        })
                    }
            }
        } ?: apiService.saveStore(ApiEndPoints.getSaveStore(), store)
            .onErrorResumeNext { error.getException(it) }.map { response ->
                Resource.Success(store.apply {
                    storeId = response.storeId
                })
            }
    }

    override fun getRetailerStoreProducts(storeId: String) {
        val path = ApiEndPoints.getRetailerStoreProducts().plus(storeId)
        apiService.getRetailerProducts(path)
            .onErrorResumeNext { error.getException(it) }
            .map { result ->
                result.product.forEach { item ->
                    Log.d(TAG, "getRetailerStoreProducts: ${item.storeId}")
                }
            }.request()
            .subscribeBy(onError = {}, onSuccess = {})
    }

    override fun insertIntoCart(item: ProviderPackage): Single<Long> {
        return cartDataSource.insert(item.toCartItem())
    }

    override fun deleteCartItem(item: ProviderPackage): Single<Int> {
        return cartDataSource.delete(item.toCartItem())
    }

    override fun getCartItems(): Flowable<List<CartEntity>> {
        return cartDataSource.getItems()
    }

    override fun getCartItemsSimple(): Single<List<CartEntity>> {
        return cartDataSource.getItemsSimple()
    }
}