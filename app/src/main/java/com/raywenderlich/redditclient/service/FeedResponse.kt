package com.raywenderlich.redditclient.service

data class RedditResponse(
    val data: RedditDataResponse
)

data class RedditDataResponse(
    val children: List<RedditChildrenResponse>,
    val after: String?,
    val before: String?
)

data class RedditChildrenResponse(
    val data: Post
)

data class Post(
    val author: String,
    val title: String,
    val num_comments: Int,
    val selftext: String,
    val url: String,
    val subreddit: String,
    val ups: Int,
    val is_video: Boolean,
    val permalink: String
)