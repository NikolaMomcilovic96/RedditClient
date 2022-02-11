package com.raywenderlich.redditclient.repository

import com.raywenderlich.redditclient.service.RedditService

class PostRepository(private val redditService: RedditService) {
    suspend fun getFromSub(name: String) = redditService.getFromSub(name)

    suspend fun getFromSubBest(name: String) = redditService.getSubSortedByBest(name)

    suspend fun getFromSubNew(name: String) = redditService.getSubSortedByNew(name)

    suspend fun getTopPosts() = redditService.getTopPosts()

    suspend fun getBestPosts() = redditService.getBestPosts()

    suspend fun getNewPosts() = redditService.getNewPosts()
}