package com.example.capstone_forum.home

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.capstone_forum.Post
import com.example.capstone_forum.R
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_make_post.*

class HomeOverviewFragment : Fragment() {

    private val posts = arrayListOf<Post>()
    private val homeFragmentAdapter = HomeOverviewAdapter(posts)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = "Home"
        setHasOptionsMenu(true)

        initViews()
    }

    private fun initViews() {
        goToMakePost.setOnClickListener {
            (context as HomeActivity).showHomeMakePostFragment()
        }
    }

}