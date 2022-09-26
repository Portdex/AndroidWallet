package com.application.portdex.data.repository

import android.content.Context
import com.application.portdex.data.errors.ErrorRepository
import com.application.portdex.data.mappers.CategoriesMapper.toCategoryItems
import com.application.portdex.data.mappers.toFeedItems
import com.application.portdex.data.remote.ApiEndPoints
import com.application.portdex.data.remote.ApiService
import com.application.portdex.data.utils.Resource
import com.application.portdex.domain.models.FeedItem
import com.application.portdex.domain.models.category.CategoryItem
import com.application.portdex.domain.repository.ServicesRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ServicesRepositoryImpl @Inject constructor(
    private val context: Context,
    private val apiService: ApiService,
    private val error: ErrorRepository
) : ServicesRepository {

    override fun getServicesProviders() {
        apiService.getRequest(ApiEndPoints.getServicesProviders())
    }

    override fun getCategories(): Single<Resource<List<CategoryItem>>> {
        return apiService.getCategoryData(ApiEndPoints.getCategoriesData())
            .onErrorResumeNext { error.getException(it) }
            .map { response ->
                Resource.Success(response.toCategoryItems(context))
            }
    }

    override fun getNewsFeedPost(): Single<Resource<List<FeedItem>>> {
        return apiService.getFeeds(ApiEndPoints.getNewsFeedPost())
            .onErrorResumeNext { error.getException(it) }
            .map { response ->
                Resource.Success(response.toFeedItems())
            }
    }
}