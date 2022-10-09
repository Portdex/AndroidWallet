package com.application.portdex.data.repository

import androidx.documentfile.provider.DocumentFile
import com.application.portdex.data.errors.ErrorRepository
import com.application.portdex.data.remote.ApiEndPoints
import com.application.portdex.data.remote.ApiService
import com.application.portdex.data.utils.Resource
import com.application.portdex.domain.models.store.StoreInfo
import com.application.portdex.domain.repository.StorageRepository
import com.application.portdex.domain.repository.StoreRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val storage: StorageRepository,
    private val error: ErrorRepository
) : StoreRepository {

    override fun uploadImage(file: DocumentFile): Single<String> {
        return storage.uploadImage(file)
    }

    override fun createStore(
        store: StoreInfo,
        imageFile: DocumentFile?
    ): Single<Resource<StoreInfo>> {
        return imageFile?.let { file ->
            uploadImage(file)
                .onErrorResumeNext { error.getException(it) }
                .flatMap { imageUrl ->
                    store.storePicUrl = imageUrl
                    apiService.saveStore(ApiEndPoints.getSaveStore(), store)
                        .onErrorResumeNext { error.getException(it) }
                        .map { Resource.Success(store) }
                }
        } ?: apiService.saveStore(ApiEndPoints.getSaveStore(), store)
            .onErrorResumeNext { error.getException(it) }
            .map { Resource.Success(store) }
    }
}