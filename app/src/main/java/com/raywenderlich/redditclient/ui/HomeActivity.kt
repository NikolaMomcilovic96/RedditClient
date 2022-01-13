package com.raywenderlich.redditclient.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.raywenderlich.redditclient.Constants
import com.raywenderlich.redditclient.R
import com.raywenderlich.redditclient.databinding.HomeActivityBinding
import com.raywenderlich.redditclient.enum.EnumClass
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

    private lateinit var binding: HomeActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView("")
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

    private fun setupRecyclerView(term: String) {
        showProgressBar()

        val service = RedditService.instance
        val repo = PostRepository(service)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        GlobalScope.launch {
            if (term.isNotEmpty()) {
                val results = repo.getFromSub(term)
                displayData(results, term)
            } else {
                val results = repo.getTopPosts()
                displayData(results, Constants.HOME_PAGE)
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
            putExtra(Constants.UPVOTES, model.data.ups)
            putExtra(Constants.COMMENTS, model.data.num_comments)
        })
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY) ?: return
            setupRecyclerView(query)
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