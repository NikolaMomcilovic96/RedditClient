package com.raywenderlich.redditclient.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.raywenderlich.redditclient.R
import com.raywenderlich.redditclient.repository.PostRepository
import com.raywenderlich.redditclient.service.RedditService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val service = RedditService.instance
        val postRepository = PostRepository(service)
        GlobalScope.launch {
            val results = postRepository.getPosts()
            Log.d("RES", "${results}")
        }
    }
}