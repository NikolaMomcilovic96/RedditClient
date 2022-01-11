package com.raywenderlich.redditclient.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.raywenderlich.redditclient.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchButton.setOnClickListener {
            val sub = binding.subTextInput.text.toString()

            if (sub.isEmpty()) {
                binding.subTextInput.error = "Add name of subreddit"
            } else {
                startActivity(Intent(this, HomeActivity::class.java).putExtra("Sub", sub))
            }
        }
    }
}