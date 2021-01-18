package com.example.capstone_forum.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone_forum.Comment
import com.example.capstone_forum.Post
import com.example.capstone_forum.R
import com.example.capstone_forum.viewmodel.CommentViewModel
import com.example.capstone_forum.viewmodel.PostViewModel
import com.example.capstone_forum.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_post_detail.*
import java.text.SimpleDateFormat
import java.util.*

class HomeDetailFragment(var post: Post) : Fragment() {

    private val userViewModel: UserViewModel by activityViewModels()
    private val postViewModel: PostViewModel by activityViewModels()
    private val commentViewModel: CommentViewModel by activityViewModels()
    private var username = ""

    private var firebase: FirebaseAuth = FirebaseAuth.getInstance()
    private var firebaseDB = FirebaseDatabase.getInstance().reference

    private val comments = arrayListOf<Comment>()
    private var homeCommentAdapter = HomeCommentAdapter(comments)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = "Home detail"
        setHasOptionsMenu(true)

        initUser()
        initViews()
    }

    private fun initUser() {
        userViewModel.getUser(firebase.currentUser!!.uid)
        userViewModel.user.observe(viewLifecycleOwner) {
            username = it.firstName
        }
    }

    private fun initViews() {
        rvComments.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = homeCommentAdapter
        }

        dataInput()

        tvCategoryPost.text = post.category
        tvPostedBy.text = getString(R.string.posted_by_s, post.creator)
        tvCreatedAt.text = dateReturn(post.timeCreated)
        tvPostTitle.text = post.title
        tvPostDescription.text = post.description
        tvLikeRatio.text = post.likeRatio.toString()

        commentBtn.setOnClickListener {
            createComment()
        }
    }

    private fun dataInput() {
        commentViewModel.getAllComments(post)

        commentViewModel.comments.observe(viewLifecycleOwner) { allComments ->
            comments.clear()
            comments.addAll(allComments)
            comments.sortBy { allPostss -> allPostss.timeCreated }
            homeCommentAdapter.notifyDataSetChanged()
        }
    }

    private fun createComment() {
        val intent = Intent(context, HomeActivity::class.java)
        val push = firebaseDB.child("posts").push()

        val newComment = Comment(push.key!!, username, System.currentTimeMillis(), addCommentInput.text.toString(), 0)

        commentViewModel.createComment(newComment, post)

        push.setValue(newComment)

        intent.putExtra("newComment", newComment)
        startActivity(intent)
    }

    @SuppressLint("SimpleDateFormat")
    fun dateReturn(long: Long): String {
        val date = Date(long)
        val format = SimpleDateFormat("dd-MM-yyyy HH:mm")
        return format.format(date)
    }

}