package com.raywenderlich.redditclient.service

data class RedditNewsResponse(
    val data: RedditDataResponse
)

data class RedditDataResponse(
    val children: List<RedditChildrenResponse>,
    val after: String?,
    val before: String?
)

data class RedditChildrenResponse(
    val data: RedditNewsDataResponse
)

data class RedditNewsDataResponse(
    val author: String,
    val title: String,
    val num_comments: Int,
    val created: Long,
    val selftext: String,
    val url: String,
    val subreddit: String,
    val ups: Int,
    val is_video: Boolean
)