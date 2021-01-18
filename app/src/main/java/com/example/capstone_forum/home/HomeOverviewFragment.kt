package com.example.capstone_forum.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone_forum.Post
import com.example.capstone_forum.R
import com.example.capstone_forum.viewmodel.PostViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.rvPosts
import kotlinx.android.synthetic.main.fragment_post_detail.*

class HomeOverviewFragment : Fragment() {

    private val postViewModel: PostViewModel by activityViewModels()

    private val posts = arrayListOf<Post>()
    private var homeFragmentAdapter = HomeOverviewAdapter(posts)

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
        recyclerView()

        goToMakePost.setOnClickListener {
            (context as HomeActivity).showHomeMakePostFragment()
        }
    }

    private fun recyclerView() {
        rvPosts.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = homeFragmentAdapter
        }

        dataInput()
    }

    private fun dataInput() {
        postViewModel.getAllPosts()

        postViewModel.posts.observe(viewLifecycleOwner) { allPosts ->
            posts.clear()
            posts.addAll(allPosts)
            posts.sortBy { allPostss -> allPostss.timeCreated }
            homeFragmentAdapter.notifyDataSetChanged()

        }


    }

}