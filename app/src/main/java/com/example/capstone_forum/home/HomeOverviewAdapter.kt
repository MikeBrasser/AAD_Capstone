package com.example.capstone_forum.home

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone_forum.Post
import com.example.capstone_forum.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.item_post_card.view.*
import java.text.SimpleDateFormat
import java.util.*

class HomeOverviewAdapter(private var posts: List<Post>) :
    RecyclerView.Adapter<HomeOverviewAdapter.ViewHolder>() {

    private lateinit var context: Context
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun init(post: Post) {
            itemView.tvCategoryPost.text = post.category
            itemView.tvCreatedAt.text = dateReturn(post.timeCreated)
            itemView.tvPostTitle.text = post.title
            itemView.tvPostedBy.text = context.getString(R.string.posted_by_s, post.creator)
            itemView.tvnumberComments.text = post.comments.size.toString()

            val liked = post.likedOrNot[firebaseAuth.currentUser!!.uid]
            if (liked != null) {
                if (liked == true) {
                    itemView.upvoteBtn.setColorFilter(ContextCompat.getColor(context, R.color.orange))
                    itemView.downvoteBtn.setColorFilter(ContextCompat.getColor(context, R.color.default_color))
                } else if (liked == false) {
                    itemView.downvoteBtn.setColorFilter(ContextCompat.getColor(context, R.color.blue))
                    itemView.upvoteBtn.setColorFilter(ContextCompat.getColor(context, R.color.default_color))
                }
            }

            val amountLiked = post.likedOrNot.values.filter { it }.size
            val amountDisliked = post.likedOrNot.values.filter { !it }.size

            val likeRatio = amountLiked - amountDisliked

            itemView.tvLikeRatio.text = likeRatio.toString()

            itemView.upvoteBtn.setOnClickListener {
                (context as HomeActivity).setLikeValue(true, post)
                notifyDataSetChanged()
            }

            itemView.downvoteBtn.setOnClickListener {
                (context as HomeActivity).setLikeValue(false, post)
                notifyDataSetChanged()
            }

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
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_post_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.init(posts[position])
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    fun updateList(updatedList: ArrayList<Post>) {
        posts = updatedList
        notifyDataSetChanged()
    }

}