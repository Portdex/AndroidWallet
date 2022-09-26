package com.application.portdex.domain.repository

import com.application.portdex.data.utils.Resource
import com.application.portdex.domain.models.FeedItem
import com.application.portdex.domain.models.category.CategoryItem
import io.reactivex.rxjava3.core.Single

interface ServicesRepository {

    fun getServicesProviders()
    fun getCategories(): Single<Resource<List<CategoryItem>>>

    fun getNewsFeedPost(): Single<Resource<List<FeedItem>>>
}