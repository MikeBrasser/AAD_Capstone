package com.example.capstone_forum.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone_forum.Post
import com.example.capstone_forum.R
import com.example.capstone_forum.categories.CategoriesActivity
import com.example.capstone_forum.notifications.NotificationsActivity
import com.example.capstone_forum.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var homeOverviewFragment: HomeOverviewFragment
    private lateinit var homeDetailFragment: HomeDetailFragment
    private lateinit var homeMakePostFragment: HomeMakePostFragment

    companion object {
        const val HOME_OVERVIEW_FRAGMENT = "HOME_OVERVIEW_FRAGMENT"
        const val HOME_DETAIL_FRAGMENT = "HOME_DETAIL_FRAGMENT"
        const val HOME_MAKE_POST_FRAGMENT = "HOME_MAKE_POST_FRAGMENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initViews()
        initNav()
    }

    private fun initViews() {
        homeOverviewFragment = HomeOverviewFragment()

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.home_target, homeOverviewFragment, HOME_OVERVIEW_FRAGMENT)
            .commit()
    }

    fun showHomeFragment() {
        homeOverviewFragment = HomeOverviewFragment()

        supportActionBar?.title = "Home"
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        supportFragmentManager.beginTransaction()
            .replace(R.id.home_target, homeOverviewFragment)
            .commit()
    }

    fun showHomeFragmentDetail(post: Post) {
        homeDetailFragment = HomeDetailFragment(post)

        supportActionBar?.title = "Post"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.home_target, homeOverviewFragment, HOME_DETAIL_FRAGMENT)
            .addToBackStack(HOME_OVERVIEW_FRAGMENT)
            .commit()
    }

    fun showHomeMakePostFragment() {
        homeMakePostFragment = HomeMakePostFragment()

        supportActionBar?.title = "Make a Post"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.home_target, homeMakePostFragment, HOME_MAKE_POST_FRAGMENT)
            .addToBackStack(HOME_OVERVIEW_FRAGMENT)
            .commit()
    }

    private fun initNav() {
        bottom_nav_home.menu.getItem(0).isChecked = true

        bottom_nav_home.setOnNavigationItemSelectedListener {
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

    override fun onSupportNavigateUp(): Boolean {
        showHomeFragment()
        return true
    }

}