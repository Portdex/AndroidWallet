package com.application.portdex.data.repository

import androidx.documentfile.provider.DocumentFile
import com.application.portdex.data.errors.ErrorRepository
import com.application.portdex.data.mappers.mapToProfileInfo
import com.application.portdex.data.remote.ApiEndPoints
import com.application.portdex.data.remote.ApiService
import com.application.portdex.data.utils.Resource
import com.application.portdex.domain.models.CreateProfileInfo
import com.application.portdex.domain.models.ProfileInfo
import com.application.portdex.domain.repository.ProfileRepository
import com.application.portdex.domain.repository.StorageRepository
import com.jacopo.pagury.prefs.PrefUtils
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val storage: StorageRepository,
    private val error: ErrorRepository
) : ProfileRepository {

    override fun getUserProfile(number: String): Single<Resource<ProfileInfo>> {
        val path = ApiEndPoints.getUserProfile().plus(number)
        return apiService.getUserProfile(path)
            .onErrorResumeNext { error.getException(it) }
            .map {
                it.firstOrNull()?.let { item ->
                    val profile = item.mapToProfileInfo()
                    PrefUtils.setProfileInfo(profile)
                    Resource.Success(profile)
                } ?: Resource.Success(ProfileInfo())
            }
    }

    override fun uploadImage(file: DocumentFile): Single<String> {
        return storage.uploadImage(file)
    }

    override fun createProfile(
        profile: CreateProfileInfo,
        imageFile: DocumentFile?
    ): Single<Resource<Boolean>> {
        return imageFile?.let { file ->
            uploadImage(file)
                .onErrorResumeNext { error.getException(it) }
                .flatMap { imageUrl ->
                    profile.profiePicUrl = imageUrl
                    apiService.saveProfile(ApiEndPoints.getSaveProfile(), profile)
                        .onErrorResumeNext { error.getException(it) }
                        .map { Resource.Success(!it.userId.isNullOrEmpty()) }
                }
        } ?: apiService.saveProfile(ApiEndPoints.getSaveProfile(), profile)
            .onErrorResumeNext { error.getException(it) }
            .map { Resource.Success(!it.userId.isNullOrEmpty()) }
    }
}