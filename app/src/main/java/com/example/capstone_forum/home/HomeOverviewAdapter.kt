package com.example.capstone_forum.home

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone_forum.Post
import com.example.capstone_forum.R
import kotlinx.android.synthetic.main.item_post_card.view.*
import java.text.SimpleDateFormat
import java.util.*

class HomeOverviewAdapter(private val posts: List<Post>):
        RecyclerView.Adapter<HomeOverviewAdapter.ViewHolder>() {

    private lateinit var context: Context

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun init(post: Post) {
            itemView.tvCategoryPost.text = post.category
            itemView.tvCreatedAt.text = dateReturn(post.timeCreated)
            itemView.tvPostTitle.text = post.title
            itemView.tvPostedBy.text = context.getString(R.string.posted_by_s, post.creator)
            itemView.tvLikeRatio.text = post.likeRatio.toString()

            itemView.setOnClickListener {
                (itemView.context as HomeActivity).showHomeFragmentDetail(post)
            }
        }

        @SuppressLint("SimpleDateFormat")
        fun dateReturn(long: Long): String {
            val date = Date(long)
            val format = SimpleDateFormat("dd-MM-yyyy HH:mm")
            return format.format(date)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
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