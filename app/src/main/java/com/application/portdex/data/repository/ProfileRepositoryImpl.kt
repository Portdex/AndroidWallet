package com.application.portdex.data.repository

import com.application.portdex.data.errors.ErrorEnum
import com.application.portdex.data.errors.ErrorRepository
import com.application.portdex.data.mappers.mapToProfileInfo
import com.application.portdex.data.remote.ApiEndPoints
import com.application.portdex.data.remote.ApiService
import com.application.portdex.data.utils.Resource
import com.application.portdex.domain.models.CreateProfileInfo
import com.application.portdex.domain.models.ProfileInfo
import com.application.portdex.domain.repository.ProfileRepository
import com.jacopo.pagury.prefs.PrefUtils
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val apiService: ApiService, private val error: ErrorRepository
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
                } ?: error.getError(ErrorEnum.noDataFound)
            }
    }

    override fun createProfile(profile: CreateProfileInfo) {

    }
}