package com.example.capstone_forum.home

import androidx.fragment.app.Fragment
import com.example.capstone_forum.Post

class HomeFragment : Fragment() {

    private val posts = arrayListOf<Post>()
    private val homeFragmentAdapter = HomeFragmentAdapter(posts)

}