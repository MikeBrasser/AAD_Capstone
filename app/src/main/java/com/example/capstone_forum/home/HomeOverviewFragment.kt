package com.example.capstone_forum.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone_forum.Post
import com.example.capstone_forum.R
import com.example.capstone_forum.viewmodel.PostViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import kotlin.collections.ArrayList

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

        pickDate.setOnClickListener {
            datePicker()
        }

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

        searchView()
    }

    private fun datePicker() {
        val builder = MaterialDatePicker.Builder.dateRangePicker()

        val picker = builder.build()
        picker.show(requireActivity().supportFragmentManager, "DATE_PICKER")

        picker.addOnPositiveButtonClickListener { select ->
            val startDate = Date(select.first!!)
            val endDate = Date(select.second!!)

            val localStart = Date(startDate.year , startDate.month, startDate.date)
            val localEnd = Date(endDate.year, endDate.month, endDate.date)

            val searchedPost = ArrayList<Post>()

            for (post in posts) {
                val postDate = Date(post.timeCreated?: System.currentTimeMillis())

                if (postDate.after(localStart) && postDate.before(localEnd)) {
                    searchedPost.add(post)
                }
            }

            homeFragmentAdapter.updateList(searchedPost)
        }
    }

    private fun searchView() {
        searchWindow.apply {
            imeOptions = EditorInfo.IME_ACTION_DONE
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    val userInput = newText?.toLowerCase()
                    val foundPost = ArrayList<Post>()

                    for (post in posts) {
                        if (post.title.toLowerCase().contains(userInput.toString())) {
                            foundPost.add(post)
                        }
                    }

                    homeFragmentAdapter.updateList(foundPost)
                    return true
                }
            })
        }
    }

}