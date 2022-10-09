package com.application.portdex.domain.repository

import androidx.documentfile.provider.DocumentFile
import com.application.portdex.data.utils.Resource
import com.application.portdex.domain.models.CreateProfileInfo
import com.application.portdex.domain.models.ProfileInfo
import io.reactivex.rxjava3.core.Single

interface ProfileRepository {

    fun uploadImage(file: DocumentFile): Single<String>

    fun getUserProfile(number: String): Single<Resource<ProfileInfo>>

    fun createProfile(
        profile: CreateProfileInfo,
        imageFile: DocumentFile?
    ): Single<Resource<ProfileInfo>>

    fun getNearByUsers(): Single<Resource<List<ProfileInfo>>>
}