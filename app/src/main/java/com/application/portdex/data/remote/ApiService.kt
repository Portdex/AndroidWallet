package com.application.portdex.data.remote

import com.application.portdex.data.remote.dto.*
import com.application.portdex.domain.models.CreateProfileInfo
import com.application.portdex.domain.models.store.StoreInfo
import io.reactivex.rxjava3.core.Single
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*


@JvmSuppressWildcards
interface ApiService {

    @POST("{endpoint}")
    fun postRequest(
        @HeaderMap headers: Map<String, Any>,
        @Path("endpoint", encoded = true) path: String,
        @Body body: RequestBody
    ): Single<Response<ResponseBody>>

    @POST("{endpoint}")
    fun postRequest(
        @Path("endpoint", encoded = true) path: String,
        @Body body: RequestBody
    ): Single<Response<ResponseBody>>

    @GET("{endpoint}")
    fun getRequest(
        @Path("endpoint", encoded = true) path: String
    ): Single<Response<ResponseBody>>

    @GET("{endpoint}")
    fun getRequest(
        @Path("endpoint", encoded = true) path: String,
        @QueryMap(encoded = true) query: Map<String, Any?>
    ): Single<Response<ResponseBody>>

    @GET("{endpoint}")
    fun getRequest(
        @HeaderMap headers: Map<String, Any>,
        @Path("endpoint", encoded = true) path: String,
    ): Single<Response<ResponseBody>>

    @GET("{endpoint}")
    fun getRequest(
        @HeaderMap headers: Map<String, Any>,
        @Path("endpoint", encoded = true) path: String,
        @QueryMap(encoded = true) query: Map<String, Any?>
    ): Single<Response<ResponseBody>>

    @GET("{endpoint}")
    fun getCategoryData(
        @Path("endpoint", encoded = true) path: String
    ): Single<CategoryDto>

    @GET("{endpoint}")
    fun getUserProfile(
        @Path("endpoint", encoded = true) path: String
    ): Single<ProfileInfoDto>

    @GET("{endpoint}")
    fun getFeeds(
        @Path("endpoint", encoded = true) path: String
    ): Single<FeedItemDto>

    @POST("{endpoint}")
    fun saveProfile(
        @Path("endpoint", encoded = true) path: String,
        @Body body: CreateProfileInfo
    ): Single<CreateProfileResponse>

    @POST("{endpoint}")
    fun saveStore(
        @Path("endpoint", encoded = true) path: String,
        @Body body: StoreInfo
    ): Single<StoreInfo>

    @GET("{endpoint}")
    fun getServiceProviders(
        @Path("endpoint", encoded = true) path: String,
    ): Single<ServiceProviderDto>

    @GET("{endpoint}")
    fun getProviderPackages(
        @Path("endpoint", encoded = true) path: String,
    ): Single<ProvidePackagesDto>

    @GET("{endpoint}")
    fun getProfileByCategory(
        @Path("endpoint", encoded = true) path: String,
    ): Single<ProfileByCategoryDto>


}