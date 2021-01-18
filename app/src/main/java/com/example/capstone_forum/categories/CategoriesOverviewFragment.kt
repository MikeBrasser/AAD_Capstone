package com.example.capstone_forum.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone_forum.R
import kotlinx.android.synthetic.main.fragment_categories_overview.*

class CategoriesOverviewFragment : Fragment() {

    private var categories = arrayListOf("Soccer fanatics", "Car enthusiasts", "Movie fans", "Computer nerds")
    private var categoriesOverviewAdapter = CategoriesOverviewAdapter(categories)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_categories_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = "Categories"
        setHasOptionsMenu(true)

        initViews()
    }

    private fun initViews() {
        rvCategories.adapter = categoriesOverviewAdapter
        rvCategories.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvCategories.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

}