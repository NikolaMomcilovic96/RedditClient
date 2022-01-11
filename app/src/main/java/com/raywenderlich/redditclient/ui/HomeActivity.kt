package com.raywenderlich.redditclient.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.raywenderlich.redditclient.R
import com.raywenderlich.redditclient.databinding.HomeActivityBinding
import com.raywenderlich.redditclient.ui.ui.main.HomeFragment

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment.newInstance())
                .commitNow()
        }
    }
}