package com.application.portdex.data.repository

import com.application.portdex.data.errors.ErrorRepository
import com.application.portdex.data.remote.ApiEndPoints
import com.application.portdex.data.remote.ApiService
import com.application.portdex.domain.models.post.CreatePost
import com.application.portdex.domain.repository.PostRepository
import com.application.portdex.domain.repository.StorageRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val storage: StorageRepository,
    private val error: ErrorRepository
) : PostRepository {

    override fun createPost(post: CreatePost) {
        apiService.createPost(ApiEndPoints.getCreatePost(), post)
    }
}