package com.example.capstone_forum.home

import androidx.fragment.app.Fragment
import com.example.capstone_forum.Post

class HomeOverviewFragment : Fragment() {

    private val posts = arrayListOf<Post>()
    private val homeFragmentAdapter = HomeOverviewAdapter(posts)

}