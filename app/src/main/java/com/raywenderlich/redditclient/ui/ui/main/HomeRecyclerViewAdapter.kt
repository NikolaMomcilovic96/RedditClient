package com.raywenderlich.redditclient.ui.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raywenderlich.redditclient.R
import com.raywenderlich.redditclient.classes.Constants
import com.raywenderlich.redditclient.classes.EnumClass
import com.raywenderlich.redditclient.service.RedditChildrenResponse
import kotlinx.android.synthetic.main.post_list_item.view.*
import kotlinx.android.synthetic.main.post_list_item.view.cardView
import kotlinx.android.synthetic.main.post_list_item.view.commentsTextView
import kotlinx.android.synthetic.main.post_list_item.view.likesTextView
import kotlinx.android.synthetic.main.post_list_item.view.shareTextView
import kotlinx.android.synthetic.main.post_list_item.view.titleTextView
import kotlinx.android.synthetic.main.post_list_item.view.userTextView

class HomeRecyclerViewAdapter(
    private val posts: MutableList<RedditChildrenResponse>,
    private val onClickListener: (RedditChildrenResponse, EnumClass) -> Unit
) : RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.post_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val upvotesNumber = "${posts[position].data.ups} ${Constants.UPVOTES}"
        val commentsNumber = "${posts[position].data.num_comments} ${Constants.COMMENTS}"
        holder.itemView.userTextView.text = posts[position].data.author
        holder.itemView.titleTextView.text = posts[position].data.title
        holder.itemView.likesTextView.text = upvotesNumber
        Glide.with(holder.itemView).load(posts[position].data.url)
            .into(holder.itemView.postImageView)
        holder.itemView.commentsTextView.text = commentsNumber

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