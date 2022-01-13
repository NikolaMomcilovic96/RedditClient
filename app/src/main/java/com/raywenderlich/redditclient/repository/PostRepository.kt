package com.raywenderlich.redditclient.repository

import com.raywenderlich.redditclient.service.RedditService

class PostRepository(private val redditService: RedditService) {
    suspend fun getFromSub(name: String) = redditService.getFromSub(name)

    suspend fun getTopPosts() = redditService.getTopPosts()
}