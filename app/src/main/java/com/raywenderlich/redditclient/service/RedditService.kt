package com.raywenderlich.redditclient.service

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface RedditService {
    @GET("r/{subreddit}/top.json")
    suspend fun getFromSub(@Path("subreddit") name: String): Response<RedditResponse>

    @GET("r/{subreddit}/best.json")
    suspend fun getSubSortedByBest(@Path("subreddit") name: String): Response<RedditResponse>

    @GET("r/{subreddit}/new.json")
    suspend fun getSubSortedByNew(@Path("subreddit") name: String): Response<RedditResponse>

    @GET("top.json")
    suspend fun getTopPosts(): Response<RedditResponse>

    @GET("best.json")
    suspend fun getBestPosts(): Response<RedditResponse>

    @GET("new.json")
    suspend fun getNewPosts(): Response<RedditResponse>

    companion object {
        val instance: RedditService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://reddit.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit.create(RedditService::class.java)
        }
    }
}