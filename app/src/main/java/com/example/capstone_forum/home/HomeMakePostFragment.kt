package com.example.capstone_forum.home

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.capstone_forum.Post
import com.example.capstone_forum.R
import com.example.capstone_forum.viewmodel.PostViewModel
import com.example.capstone_forum.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_make_post.*

class HomeMakePostFragment : Fragment() {

    private val userViewModel: UserViewModel by activityViewModels()
    private val postViewModel: PostViewModel by activityViewModels()

    private val spinnerList = arrayListOf("Soccer", "Cars", "Movies", "Computers")

    private var firebase: FirebaseAuth = FirebaseAuth.getInstance()

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
    }

    private fun initViews() {
        srCategory.adapter = ArrayAdapter<String>(requireContext(), R.layout.dropdown_spinner, spinnerList)

        fabMakePost.setOnClickListener {
            if (inputValidation()) {
                makePost()

                Toast.makeText(context, "Post created", Toast.LENGTH_SHORT).show()
                (context as HomeActivity).showHomeFragment()
            }
        }
    }

    private fun makePost() {
        userViewModel.getUser(firebase.currentUser!!.uid)

        userViewModel.user.observe(viewLifecycleOwner, Observer { user ->
            postViewModel.post.observe(viewLifecycleOwner, Observer {
                postViewModel.createPost(Post(it.id, srCategory.selectedItem.toString(), user.firstName, it.timeCreated, tiTitle.text.toString(), tiDescription.text.toString(), 0))
            })
        })
    }

    private fun inputValidation(): Boolean {
        return if (tiTitle.text.toString().isBlank()) {
            tiTitle.error = "Title field cannot be empty"
            false
        } else true
    }

}