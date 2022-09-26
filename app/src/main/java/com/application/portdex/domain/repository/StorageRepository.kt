package com.application.portdex.domain.repository

import androidx.documentfile.provider.DocumentFile
import io.reactivex.rxjava3.core.Single

interface StorageRepository {

    fun uploadImage(file: DocumentFile): Single<String>
}