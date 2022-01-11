package com.raywenderlich.redditclient.ui.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raywenderlich.redditclient.databinding.PostListItemBinding
import com.raywenderlich.redditclient.service.RedditChildrenResponse
import kotlinx.android.synthetic.main.post_list_item.view.*

class HomeRecyclerViewAdapter(
    val posts: MutableList<RedditChildrenResponse>
) : RecyclerView.Adapter<HomeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = PostListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.itemView.userTextView.text = posts[position].data.author
        holder.itemView.titleTextView.text = posts[position].data.title
        Glide.with(holder.itemView).load(posts[position].data.url)
            .into(holder.itemView.postImageView)
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}