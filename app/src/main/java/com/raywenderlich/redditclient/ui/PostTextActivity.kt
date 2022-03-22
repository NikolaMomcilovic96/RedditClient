package com.raywenderlich.redditclient.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import com.raywenderlich.redditclient.classes.Constants
import com.raywenderlich.redditclient.databinding.ActivityPostTextBinding

class PostTextActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val binding = ActivityPostTextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sub = intent.getStringExtra(Constants.SUBREDDIT)
        val text = intent.getStringExtra(Constants.POST_TEXT)
        val author = intent.getStringExtra(Constants.AUTHOR)
        val upvotes = intent.getStringExtra(Constants.UPVOTES)
        val title = intent.getStringExtra(Constants.TITLE)
        val comments = intent.getStringExtra(Constants.COMMENTS)
        val permalink = intent.getStringExtra(Constants.LINK)

        val subText = "/r/$sub"
        val user = "/u/$author"
        val upvotesNumber = "$upvotes ${Constants.UPVOTES}"
        val commentsNumber = "$comments ${Constants.COMMENTS}"
        binding.subTextView.text = subText
        binding.textTextView.text = text
        binding.authorTextView.text = user
        binding.postTitleTextView.text = title
        binding.upvotesTextView.text = upvotesNumber
        binding.numComTextView.text = commentsNumber

        binding.shareButtonTextView.setOnClickListener {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, permalink)
                type = Constants.PLAIN_TEXT
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }
}