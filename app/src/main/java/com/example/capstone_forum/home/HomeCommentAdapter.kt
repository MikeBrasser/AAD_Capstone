package com.example.capstone_forum.home

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone_forum.Comment
import com.example.capstone_forum.R
import kotlinx.android.synthetic.main.fragment_post_detail.view.*
import kotlinx.android.synthetic.main.item_comment.view.*
import java.text.SimpleDateFormat
import java.util.*

class HomeCommentAdapter(private val comments: List<Comment>):
    RecyclerView.Adapter<HomeCommentAdapter.ViewHolder>() {

    private lateinit var context: Context

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun init(comment: Comment) {
            itemView.tvUserComment.text = comment.creator
            itemView.tvPostedAtComment.text = dateReturn(comment.timeCreated)
            itemView.tvComment.text = comment.comment
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
            .inflate(R.layout.item_comment, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.init(comments[position])
    }

    override fun getItemCount(): Int {
        return comments.size
    }

}