package com.raywenderlich.redditclient.ui.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.raywenderlich.redditclient.databinding.MainFragmentBinding
import com.raywenderlich.redditclient.repository.PostRepository
import com.raywenderlich.redditclient.service.RedditChildrenResponse
import com.raywenderlich.redditclient.service.RedditService
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
                    binding.recyclerView.adapter = HomeRecyclerViewAdapter(posts)
                    binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                }
            }
        }

        return binding.root
    }

}