package com.example.capstone_forum.categories

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone_forum.R
import com.example.capstone_forum.home.HomeActivity
import com.example.capstone_forum.home.HomeDetailFragment
import com.example.capstone_forum.notifications.NotificationsActivity
import com.example.capstone_forum.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_categories.*

class CategoriesActivity : AppCompatActivity() {

    private lateinit var categoriesOverviewFragment: CategoriesOverviewFragment
    private lateinit var categoriesDetailFragment: CategoriesDetailFragment

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

        supportActionBar?.title = "Category"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.categories_target, categoriesDetailFragment, CATEGORIES_DETAIL)
            .addToBackStack(CATEGORIES_OVERVIEW)
            .commit()
    }

    private fun initNav() {
        //Set current active menu item
        bottom_nav_categories.menu.getItem(1).isChecked = true

        bottom_nav_categories.setOnNavigationItemSelectedListener {
            when (it.toString()) {
                "Home" -> startActivity(Intent(this, HomeActivity::class.java))
                "Categories" -> startActivity(Intent(this, CategoriesActivity::class.java))
                "Notifications" -> startActivity(Intent(this, NotificationsActivity::class.java))
                "Settings" -> startActivity(Intent(this, SettingsActivity::class.java))
            }

            overridePendingTransition(0, 0)
            false
        }
    }
}