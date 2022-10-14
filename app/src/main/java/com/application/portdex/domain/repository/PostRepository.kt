package com.application.portdex.domain.repository

import com.application.portdex.data.utils.Resource
import com.application.portdex.domain.models.post.CreatePost
import io.reactivex.rxjava3.core.Single

interface PostRepository {

    fun createPost(post: CreatePost): Single<Resource<Boolean>>
}