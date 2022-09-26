package com.application.portdex.domain.repository

import androidx.documentfile.provider.DocumentFile
import com.application.portdex.domain.models.CreateProfileInfo
import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody
import retrofit2.Response

interface RegistrationRepository {

    fun uploadImage(file: DocumentFile): Single<String>

    fun createProfile(profile: CreateProfileInfo): Single<Response<ResponseBody>>
}