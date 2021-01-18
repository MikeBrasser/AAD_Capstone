package com.example.capstone_forum.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.capstone_forum.Post
import com.example.capstone_forum.R
import com.example.capstone_forum.viewmodel.PostViewModel
import com.example.capstone_forum.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_make_post.*
import kotlinx.android.synthetic.main.item_post_card.*
import java.time.LocalDate
import java.time.LocalDateTime

class HomeMakePostFragment : Fragment() {

    private val userViewModel: UserViewModel by activityViewModels()
    private val postViewModel: PostViewModel by activityViewModels()

    private var username = ""

    private val spinnerList =
        arrayListOf("Soccer_fanatics", "Car_enthusiasts", "Movie_fans", "Computer_nerds")

    private var firebase: FirebaseAuth = FirebaseAuth.getInstance()
    private var firebaseDB = FirebaseDatabase.getInstance().reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_make_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = "Make a Post"
        setHasOptionsMenu(true)

        initViews()
        initUser()
    }

    private fun initUser() {
        userViewModel.getUser(firebase.currentUser!!.uid)
        userViewModel.user.observe(viewLifecycleOwner) {
            username = it.firstName
        }
    }

    private fun initViews() {
        srCategory.adapter =
            ArrayAdapter<String>(requireContext(), R.layout.dropdown_spinner, spinnerList)

        fabMakePost.setOnClickListener {
            if (inputValidation()) {
                makePost()

                Toast.makeText(context, "Post created", Toast.LENGTH_SHORT).show()
                (context as HomeActivity).showHomeFragment()
            }
        }
    }

    private fun makePost() {
        val push = firebaseDB.child("posts").push()

        val newPost = Post(
            push.key!!,
            srCategory.selectedItem.toString(),
            username,
            System.currentTimeMillis(),
            tiTitle.text.toString(),
            tiDescription.text.toString(),
            0
        )

        postViewModel.createPost(newPost)

        push.setValue(newPost)
    }

    private fun inputValidation(): Boolean {
        return if (tiTitle.text.toString().isBlank()) {
            tiTitle.error = "Title field cannot be empty"
            false
        } else true
    }

}