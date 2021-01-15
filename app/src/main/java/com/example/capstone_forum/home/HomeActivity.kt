package com.example.capstone_forum.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone_forum.R
import com.example.capstone_forum.categories.CategoriesActivity
import com.example.capstone_forum.notifications.NotificationsActivity
import com.example.capstone_forum.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var homeOverviewFragment: HomeOverviewFragment
    private lateinit var homeDetailFragment: HomeDetailFragment

    companion object {
        const val HOME_FRAGMENT = "HOME_FRAGMENT"
        const val HOME_FRAGMENT_DETAIL = "HOME_FRAGMENT_DETAIL"
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
        supportFragmentManager.beginTransaction().setReorderingAllowed(true)
            .replace(R.id.home_target, homeOverviewFragment, HOME_FRAGMENT)
            .commit()
    }

    private fun showHomeFragment() {
        homeOverviewFragment = HomeOverviewFragment()

        supportActionBar?.title = "Home"
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        supportFragmentManager.beginTransaction()
            .replace(R.id.home_target, homeOverviewFragment)
            .commit()
    }

    private fun showHomeFragmentDetail() {
        homeDetailFragment = HomeDetailFragment()

        supportActionBar?.title = "Post"
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        supportFragmentManager.beginTransaction()
            .replace(R.id.home_target, homeOverviewFragment)
            .commit()
    }

    private fun initNav() {
        //Set current active menu item
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

}