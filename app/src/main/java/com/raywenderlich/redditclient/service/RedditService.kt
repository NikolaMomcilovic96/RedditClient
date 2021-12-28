package com.raywenderlich.redditclient.service

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RedditService {
    @GET("/r/serbia/.rss")
    suspend fun getPosts(): Response<FeedResponse>

    companion object {
        val instance: RedditService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://reddit.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit.create(RedditService::class.java)
        }
    }
}