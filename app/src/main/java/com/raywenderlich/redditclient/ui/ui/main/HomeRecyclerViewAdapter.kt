package com.raywenderlich.redditclient.ui.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raywenderlich.redditclient.databinding.PostListItemBinding
import com.raywenderlich.redditclient.enum.EnumClass
import com.raywenderlich.redditclient.service.RedditChildrenResponse
import kotlinx.android.synthetic.main.post_list_item.view.*

class HomeRecyclerViewAdapter(
    private val posts: MutableList<RedditChildrenResponse>,
    private val onClickListener: (RedditChildrenResponse, EnumClass) -> Unit
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
        holder.itemView.likesTextView.text = "${posts[position].data.ups} upvotes"
        if (posts[position].data.is_video) {
            holder.itemView.postTextView.text = "Video"
        } else {
            holder.itemView.postTextView.text = posts[position].data.selftext
        }
        Glide.with(holder.itemView).load(posts[position].data.url)
            .into(holder.itemView.postImageView)
        holder.itemView.commentsTextView.text = "${posts[position].data.num_comments} comments"

        holder.itemView.cardView.setOnClickListener {
            onClickListener(posts[position], EnumClass.Card)
        }
        holder.itemView.shareTextView.setOnClickListener {
            onClickListener(posts[position], EnumClass.Share)
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}