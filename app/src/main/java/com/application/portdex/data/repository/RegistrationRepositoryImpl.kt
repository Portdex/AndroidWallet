package com.application.portdex.data.repository

import androidx.documentfile.provider.DocumentFile
import com.application.portdex.data.errors.ErrorRepository
import com.application.portdex.data.remote.ApiEndPoints
import com.application.portdex.data.remote.ApiService
import com.application.portdex.domain.models.CreateProfileInfo
import com.application.portdex.domain.repository.RegistrationRepository
import com.application.portdex.domain.repository.StorageRepository
import com.jacopo.pagury.prefs.PrefUtils
import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val storage: StorageRepository,
    private val error: ErrorRepository
) : RegistrationRepository {

    override fun uploadImage(file: DocumentFile): Single<String> {
        return storage.uploadImage(file)
    }

    override fun createProfile(profile: CreateProfileInfo): Single<Response<ResponseBody>> {
        val location = PrefUtils.getLocation()
        val params = mutableMapOf<String, Any?>().apply {
            put("phoneNo", profile.phoneNo)
            put("country", profile.country)
            put("firstName", profile.firstName)
            put("lastName", profile.lastName)
            put("email", profile.email)
            put("profiePicUrl", profile.profiePicUrl)
            put("category", profile.category)
            put("subCategory", profile.subCategory)
            put("latitude", location?.latitude)
            put("longitude", location?.longitude)
            put("storeId", profile.storeId)
            put("userToken", profile.userToken)
            put("signedUpUser", profile.signedUpUser)

        }
        return profile.imageFile?.let { file ->
            uploadImage(file)
                .onErrorResumeNext { error.getException(it) }
                .flatMap { imageUrl ->
                    params["profiePicUrl"] = imageUrl
                    apiService.saveProfile(ApiEndPoints.getSaveProfile(), params)
                }
        } ?: apiService.saveProfile(ApiEndPoints.getSaveProfile(), params)
    }
}