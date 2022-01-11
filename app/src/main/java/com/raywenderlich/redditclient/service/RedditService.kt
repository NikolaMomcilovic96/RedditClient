package com.raywenderlich.redditclient.service

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface RedditService {
    @GET("r/{subreddit}.json")
    suspend fun getAllPosts(@Path("subreddit") name: String): Response<RedditNewsResponse>

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