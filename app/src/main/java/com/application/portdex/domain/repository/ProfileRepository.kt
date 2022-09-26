package com.application.portdex.domain.repository

import com.application.portdex.data.utils.Resource
import com.application.portdex.domain.models.CreateProfileInfo
import com.application.portdex.domain.models.ProfileInfo
import io.reactivex.rxjava3.core.Single

interface ProfileRepository {

    fun getUserProfile(number: String): Single<Resource<ProfileInfo>>

    fun createProfile(profile: CreateProfileInfo)
}