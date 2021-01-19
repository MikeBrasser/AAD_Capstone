package com.example.capstone_forum.categories

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone_forum.Post
import com.example.capstone_forum.R
import com.example.capstone_forum.home.HomeActivity
import com.example.capstone_forum.home.HomeDetailFragment
import com.example.capstone_forum.settings.SettingsActivity
import com.example.capstone_forum.viewmodel.PostViewModel
import kotlinx.android.synthetic.main.activity_categories.*

class CategoriesActivity : AppCompatActivity() {

    private lateinit var categoriesOverviewFragment: CategoriesOverviewFragment
    private lateinit var categoriesDetailFragment: CategoriesDetailFragment
    private val postViewModel: PostViewModel by viewModels()

    companion object {
        const val CATEGORIES_OVERVIEW = "CATEGORIES_OVERVIEW"
        const val CATEGORIES_DETAIL = "CATEGORIES_DETAIL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        initViews()
        initNav()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (supportActionBar?.title == "Category") {
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                } else if (supportActionBar?.title == "Post") {
                    supportActionBar?.title = "Category"
                    bottom_nav_categories.visibility = View.VISIBLE
                }
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initViews() {
        categoriesOverviewFragment = CategoriesOverviewFragment()

        supportActionBar?.title = "Categories"
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.categories_target, categoriesOverviewFragment, CATEGORIES_OVERVIEW)
            .commit()
    }

    fun showCategoriesDetail(filter: String) {
        categoriesDetailFragment = CategoriesDetailFragment(filter)

        supportActionBar?.title = filter
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        bottom_nav_categories.visibility = View.VISIBLE

        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.categories_target, categoriesDetailFragment, CATEGORIES_DETAIL)
            .addToBackStack(CATEGORIES_OVERVIEW)
            .commit()
    }

    fun showCategoriesFragmentDetailDetail(post: Post) {
        val homeDetailFragment = HomeDetailFragment(post)

        supportActionBar?.title = "Post"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        bottom_nav_categories.visibility = View.GONE

        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.categories_target, homeDetailFragment, HomeActivity.HOME_DETAIL_FRAGMENT)
            .addToBackStack(HomeActivity.HOME_OVERVIEW_FRAGMENT)
            .commit()
    }

    fun setLikeValue(liked: Boolean, post: Post) {
        postViewModel.setLiked(liked, post)
    }

    private fun initNav() {
        //Set current active menu item
        bottom_nav_categories.menu.getItem(1).isChecked = true

        bottom_nav_categories.setOnNavigationItemSelectedListener {
            when (it.toString()) {
                "Home" -> startActivity(Intent(this, HomeActivity::class.java))
                "Categories" -> startActivity(Intent(this, CategoriesActivity::class.java))
                "Settings" -> startActivity(Intent(this, SettingsActivity::class.java))
            }

            overridePendingTransition(0, 0)
            false
        }
    }
}