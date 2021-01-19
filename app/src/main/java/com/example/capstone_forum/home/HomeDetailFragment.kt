package com.example.capstone_forum.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.inputmethodservice.Keyboard
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
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
import kotlinx.android.synthetic.main.item_post_card.view.*
import java.text.SimpleDateFormat
import java.util.*

class HomeDetailFragment(var post: Post) : Fragment() {

    private val userViewModel: UserViewModel by activityViewModels()
    private val postViewModel: PostViewModel by activityViewModels()
    private val commentViewModel: CommentViewModel by activityViewModels()
    private var username = ""
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private var firebase: FirebaseAuth = FirebaseAuth.getInstance()

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

        upvoteBtn.setOnClickListener {
            (context as HomeActivity).setLikeValue(true, post)
        }

        downvoteBtn.setOnClickListener {
            (context as HomeActivity).setLikeValue(false, post)
        }

        commentBtn.setOnClickListener {
            createComment()
        }
    }

    private fun dataInput() {
        postViewModel.getPost(post.id)
        postViewModel.post.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            if (post.id == it.id) {
                tvCategoryPost.text = post.category
                tvPostedBy.text = getString(R.string.posted_by_s, post.creator)
                tvCreatedAt.text = dateReturn(post.timeCreated)
                tvPostTitle.text = post.title
                tvPostDescription.text = post.description
                tvNumberComments.text = post.comments.size.toString()

                val liked = post.likedOrNot[firebaseAuth.currentUser!!.uid]
                if (liked != null) {
                    if (liked == true) {
                        upvoteBtn.setColorFilter(ContextCompat.getColor(requireContext(), R.color.orange))
                        downvoteBtn.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black))
                    } else if (liked == false) {
                        downvoteBtn.setColorFilter(ContextCompat.getColor(requireContext(), R.color.blue))
                        upvoteBtn.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black))
                    }
                }

                val amountLiked = post.likedOrNot.values.filter { it }.size
                val amountDisliked = post.likedOrNot.values.filter { !it }.size

                val likeRatio = amountLiked - amountDisliked

                tvLikeRatio.text = likeRatio.toString()

                comments.clear()
                comments.addAll(it.comments)
                comments.sortBy { allPostss -> allPostss.timeCreated }
                homeCommentAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun createComment() {
        val newComment = Comment(username, System.currentTimeMillis(), addCommentInput.text.toString())

        post.comments.add(newComment)

        commentViewModel.createComment(post)

        addCommentInput.text.clear()
        hideKeyboard()
    }

    private fun hideKeyboard() {
        val inputManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    @SuppressLint("SimpleDateFormat")
    fun dateReturn(long: Long): String {
        val date = Date(long)
        val format = SimpleDateFormat("dd-MM-yyyy HH:mm")
        return format.format(date)
    }

}