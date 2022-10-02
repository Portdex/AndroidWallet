package com.application.portdex.data.repository

import android.content.Context
import com.application.portdex.core.utils.RxUtils.request
import com.application.portdex.data.errors.ErrorRepository
import com.application.portdex.data.mappers.CategoriesMapper.toCategoryItems
import com.application.portdex.data.mappers.mapToProviderInfo
import com.application.portdex.data.mappers.mapToProviders
import com.application.portdex.data.mappers.toFeedItems
import com.application.portdex.data.mappers.toProviderPackages
import com.application.portdex.data.remote.ApiEndPoints
import com.application.portdex.data.remote.ApiService
import com.application.portdex.data.utils.Resource
import com.application.portdex.domain.models.FeedItem
import com.application.portdex.domain.models.ProviderInfo
import com.application.portdex.domain.models.ProviderPackage
import com.application.portdex.domain.models.category.CategoryItem
import com.application.portdex.domain.repository.ServicesRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ServicesRepositoryImpl @Inject constructor(
    private val context: Context,
    private val apiService: ApiService,
    private val error: ErrorRepository
) : ServicesRepository {

    override fun getServicesProviders(): Single<Resource<List<ProviderInfo>>> {
        return apiService.getServiceProviders(ApiEndPoints.getServicesProviders())
            .onErrorResumeNext { error.getException(it) }
            .map { response -> Resource.Success(response.mapToProviderInfo()) }
    }

    override fun getProfileByCategory(category: String): Single<Resource<List<ProviderInfo>>> {
        return apiService.getProfileByCategory(ApiEndPoints.getProfileByCategory().plus(category))
            .request().onErrorResumeNext { error.getException(it) }
            .map { result -> Resource.Success(result.mapToProviders()) }
    }

    override fun getCategories(): Single<Resource<List<CategoryItem>>> {
        return apiService.getCategoryData(ApiEndPoints.getCategoriesData())
            .onErrorResumeNext { error.getException(it) }
            .map { response -> Resource.Success(response.toCategoryItems(context)) }
    }

    override fun getNewsFeedPost(): Single<Resource<List<FeedItem>>> {
        return apiService.getFeeds(ApiEndPoints.getNewsFeedPost())
            .onErrorResumeNext { error.getException(it) }
            .map { response -> Resource.Success(response.toFeedItems()) }
    }

    override fun getProviderPackages(userId: String): Single<Resource<List<ProviderPackage>>> {
        return apiService.getProviderPackages(ApiEndPoints.getProvidePackages().plus(userId))
            .request().onErrorResumeNext { error.getException(it) }
            .map { result -> Resource.Success(result.toProviderPackages()) }
    }
}