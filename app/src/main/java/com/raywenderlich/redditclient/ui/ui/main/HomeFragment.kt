package com.raywenderlich.redditclient.ui.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.raywenderlich.redditclient.databinding.MainFragmentBinding
import com.raywenderlich.redditclient.enum.EnumClass
import com.raywenderlich.redditclient.repository.PostRepository
import com.raywenderlich.redditclient.service.RedditChildrenResponse
import com.raywenderlich.redditclient.service.RedditService
import com.raywenderlich.redditclient.ui.PostDetailActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private lateinit var binding: MainFragmentBinding

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)

        val sub = activity?.intent?.getStringExtra("Sub").toString()

        val service = RedditService.instance
        val repo = PostRepository(service)

        GlobalScope.launch {
            val result = repo.getAllPosts(sub)
            val posts = mutableListOf<RedditChildrenResponse>()

            if (result.isSuccessful) {
                withContext(Dispatchers.Main) {
                    binding.subredditTextView.text = sub
                    result.body()?.data?.children?.forEach {
                        posts.add(it)
                    }
                    binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    binding.recyclerView.adapter =
                        HomeRecyclerViewAdapter(posts) { post, clicked ->
                            when (clicked) {
                                EnumClass.Card -> startActivity(
                                    Intent(
                                        activity,
                                        PostDetailActivity::class.java
                                    ).apply {
                                        putExtra("Title", post.data.title)
                                        putExtra("Author", post.data.author)
                                        putExtra("PostImage", post.data.url)
                                        putExtra("PostText", post.data.selftext)
                                        putExtra("Subreddit", post.data.subreddit)
                                        putExtra("Upvotes", post.data.ups)
                                        putExtra("Comments", post.data.num_comments)
                                    }
                                )
                                EnumClass.Share -> {
                                    val sendIntent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        putExtra(Intent.EXTRA_TEXT, post.data.url)
                                        type = "text/plain"
                                    }
                                    val shareIntent = Intent.createChooser(sendIntent, null)
                                    startActivity(shareIntent)
                                }
                            }
                        }
                }
            }
        }

        return binding.root
    }

}