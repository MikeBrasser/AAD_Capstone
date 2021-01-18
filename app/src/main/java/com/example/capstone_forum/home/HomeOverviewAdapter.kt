package com.example.capstone_forum.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone_forum.Post
import com.example.capstone_forum.R
import kotlinx.android.synthetic.main.item_post_card.view.*

class HomeOverviewAdapter(private val posts: List<Post>):
        RecyclerView.Adapter<HomeOverviewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun init(post: Post) {
            itemView.tvCategoryPost.text = post.category
            itemView.tvCreatedAt.text = post.timeCreated.toString()
            itemView.tvPostTitle.text = post.title
            itemView.tvPostedBy.text = post.creator
            itemView.likeRatio.text = post.likeRatio.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post_card, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.init(posts[position])
    }

    override fun getItemCount(): Int {
        return posts.size
    }

}