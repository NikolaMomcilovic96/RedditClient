package com.raywenderlich.redditclient.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import com.bumptech.glide.Glide
import com.raywenderlich.redditclient.classes.Constants
import com.raywenderlich.redditclient.databinding.ActivityPostDetailBinding

class PostDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding = ActivityPostDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sub = intent.getStringExtra(Constants.SUBREDDIT)
        val author = intent.getStringExtra(Constants.AUTHOR)
        val image = intent.getStringExtra(Constants.POST_IMAGE)
        val upvotes = intent.getStringExtra(Constants.UPVOTES)
        val title = intent.getStringExtra(Constants.TITLE)
        val comments = intent.getStringExtra(Constants.COMMENTS)

        val upvotesNumber = "$upvotes ${Constants.UPVOTES}"
        val commentsNumber = "$comments ${Constants.COMMENTS}"
        binding.subTextView.text = sub
        binding.authorTextView.text = author
        binding.postTitleTextView.text = title
        binding.upvotesTextView.text = upvotesNumber
        binding.numComTextView.text = commentsNumber
        Glide.with(binding.imageView).load(image).into(binding.imageView)
    }
}