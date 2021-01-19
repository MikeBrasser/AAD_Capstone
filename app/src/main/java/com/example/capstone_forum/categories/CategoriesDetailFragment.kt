package com.example.capstone_forum.categories

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
import com.example.capstone_forum.home.HomeActivity
import com.example.capstone_forum.home.HomeOverviewAdapter
import com.example.capstone_forum.viewmodel.PostViewModel
import kotlinx.android.synthetic.main.fragment_categories_detail.*
import kotlinx.android.synthetic.main.fragment_home.*

class CategoriesDetailFragment(var categoryFilter: String) : Fragment() {

    private val postViewModel: PostViewModel by activityViewModels()

    private val posts = arrayListOf<Post>()
    private var categoriesDetailAdapter = CategoriesDetailAdapter(posts)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_categories_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = "Categories"
        setHasOptionsMenu(true)

        initViews()
    }

    private fun initViews() {
        rvPostsFilter.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = categoriesDetailAdapter
        }

        dataInput()
    }

    private fun dataInput() {
        postViewModel.getAllPosts()

        postViewModel.posts.observe(viewLifecycleOwner) { allPosts ->
            posts.clear()
            val filter = allPosts.filter { it.category == categoryFilter }
            posts.addAll(filter)
            posts.sortBy { allPostss -> allPostss.timeCreated }
            categoriesDetailAdapter.notifyDataSetChanged()
        }
    }

}