package com.application.portdex.domain.repository

import androidx.documentfile.provider.DocumentFile
import com.application.portdex.data.utils.Resource
import com.application.portdex.domain.models.store.StoreInfo
import io.reactivex.rxjava3.core.Single

interface StoreRepository {

    fun uploadImage(file: DocumentFile): Single<String>
    fun createStore(store: StoreInfo, imageFile: DocumentFile? = null): Single<Resource<StoreInfo>>
}