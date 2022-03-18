package com.raywenderlich.redditclient.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.widget.SearchView
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.raywenderlich.redditclient.classes.Constants
import com.raywenderlich.redditclient.R
import com.raywenderlich.redditclient.databinding.HomeActivityBinding
import com.raywenderlich.redditclient.classes.EnumClass
import com.raywenderlich.redditclient.repository.PostRepository
import com.raywenderlich.redditclient.service.RedditChildrenResponse
import com.raywenderlich.redditclient.service.RedditResponse
import com.raywenderlich.redditclient.service.RedditService
import com.raywenderlich.redditclient.ui.ui.main.HomeRecyclerViewAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    //test

    private lateinit var binding: HomeActivityBinding
    private lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        spinner = binding.sortOptions
        val options = arrayOf("Top", "Best", "New")
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, options)

        setupRecyclerView("", "Top")

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                p1: View?,
                position: Int,
                p3: Long
            ) {
                val term = options[position]
                if (binding.subredditTextView.text == Constants.HOME_PAGE) {
                    setupRecyclerView("", term)
                } else {
                    setupRecyclerView(binding.subredditTextView.text.toString(), term)
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }

        }

        handleIntent(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)
        val searchMenuItem = menu.findItem(R.id.search_item)
        val searchView = searchMenuItem?.actionView as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        return true
    }

    private fun setupRecyclerView(sub: String, sort: String) {
        showProgressBar()

        val service = RedditService.instance
        val repo = PostRepository(service)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        GlobalScope.launch {
            if (sub.isEmpty()) {
                when (sort) {
                    "Top" -> {
                        val results = repo.getTopPosts()
                        displayData(results, Constants.HOME_PAGE)
                    }
                    "Best" -> {
                        val results = repo.getBestPosts()
                        displayData(results, Constants.HOME_PAGE)
                    }
                    "New" -> {
                        val results = repo.getNewPosts()
                        displayData(results, Constants.HOME_PAGE)
                    }
                }
            } else {
                when (sort) {
                    "Top" -> {
                        val results = repo.getFromSub(sub)
                        displayData(results, sub)
                    }
                    "Best" -> {
                        val results = repo.getFromSubBest(sub)
                        displayData(results, sub)
                    }
                    "New" -> {
                        val results = repo.getFromSubNew(sub)
                        displayData(results, sub)
                    }
                }
            }
        }
    }

    private fun displayData(results: Response<RedditResponse>, sub: String) {
        val posts = mutableListOf<RedditChildrenResponse>()
        GlobalScope.launch {
            if (results.isSuccessful) {
                withContext(Dispatchers.Main) {
                    hideProgressBar()
                    binding.subredditTextView.text = sub
                    results.body()?.data?.children?.forEach {
                        posts.add(it)
                    }
                    binding.recyclerView.adapter = HomeRecyclerViewAdapter(posts) { post, clicked ->
                        when (clicked) {
                            EnumClass.Card -> openPostActivity(post)
                            EnumClass.Share -> {
                                val sendIntent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, post.data.url)
                                    type = Constants.PLAIN_TEXT
                                }
                                val shareIntent = Intent.createChooser(sendIntent, null)
                                startActivity(shareIntent)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun openPostActivity(model: RedditChildrenResponse) {
        startActivity(Intent(this, PostDetailActivity::class.java).apply {
            putExtra(Constants.TITLE, model.data.title)
            putExtra(Constants.AUTHOR, model.data.author)
            putExtra(Constants.POST_IMAGE, model.data.url)
            putExtra(Constants.POST_TEXT, model.data.selftext)
            putExtra(Constants.SUBREDDIT, model.data.subreddit)
            putExtra(Constants.UPVOTES, model.data.ups.toString())
            putExtra(Constants.COMMENTS, model.data.num_comments.toString())
        })
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY) ?: return
            setupRecyclerView(query, "Top")
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }
}