package com.application.portdex.domain.repository

import com.application.portdex.data.utils.Resource
import com.application.portdex.domain.models.FeedItem
import com.application.portdex.domain.models.ProviderInfo
import com.application.portdex.domain.models.ProviderPackage
import com.application.portdex.domain.models.category.CategoryItem
import io.reactivex.rxjava3.core.Single

interface ServicesRepository {

    fun getServicesProviders(): Single<Resource<List<ProviderInfo>>>
    fun getProfileByCategory(category: String): Single<Resource<List<ProviderInfo>>>
    fun getCategories(): Single<Resource<List<CategoryItem>>>
    fun getNewsFeedPost(): Single<Resource<List<FeedItem>>>
    fun getProviderPackages(userId: String): Single<Resource<List<ProviderPackage>>>
}