package com.application.portdex.domain.repository

import com.application.portdex.domain.models.post.CreatePost

interface PostRepository {

    fun createPost(post: CreatePost)
}