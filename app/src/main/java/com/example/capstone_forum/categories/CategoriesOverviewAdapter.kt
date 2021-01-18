package com.example.capstone_forum.categories

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone_forum.R
import kotlinx.android.synthetic.main.item_category.view.*

class CategoriesOverviewAdapter(private val categories: List<String>):
    RecyclerView.Adapter<CategoriesOverviewAdapter.ViewHolder>() {

    private lateinit var context: Context


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun init(category: String) {
            itemView.tvCategory.text = category

            itemView.setOnClickListener {
                (itemView.context as CategoriesActivity).showCategoriesDetail(category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_category, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.init(categories[position])
    }

    override fun getItemCount(): Int {
        return categories.size
    }
}