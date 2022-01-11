package com.raywenderlich.redditclient.repository

import com.raywenderlich.redditclient.service.RedditService

class PostRepository(private val redditService: RedditService) {
    suspend fun getAllPosts(name: String) = redditService.getAllPosts(name)
}