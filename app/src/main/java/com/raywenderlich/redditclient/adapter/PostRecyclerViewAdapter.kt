package com.raywenderlich.redditclient.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.redditclient.R
import com.raywenderlich.redditclient.service.RedditChildrenResponse
import kotlinx.android.synthetic.main.post_list_item.view.*

class PostRecyclerViewAdapter : RecyclerView.Adapter<PostRecyclerViewAdapter.MyViewHolder>() {

    private var postsList = emptyList<RedditChildrenResponse>()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.post_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.userTextView.text = postsList[position].data.author
        holder.itemView.titleTextView.text = postsList[position].data.title
    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    fun setData(posts: List<RedditChildrenResponse>) {
        postsList = posts
    }
}