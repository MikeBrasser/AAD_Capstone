package com.example.capstone_forum.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone_forum.R
import com.example.capstone_forum.categories.CategoriesActivity
import com.example.capstone_forum.notification.NotificationActivity
import com.example.capstone_forum.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var homeFragment: HomeFragment
    private lateinit var homeFragmentDetail: HomeFragmentDetail

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
        homeFragment = HomeFragment()

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportFragmentManager.beginTransaction().setReorderingAllowed(true)
            .replace(R.id.home_target, homeFragment, HOME_FRAGMENT)
            .commit()
    }

    private fun showHomeFragment() {
        homeFragment = HomeFragment()

        supportActionBar?.title = "Home"
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        supportFragmentManager.beginTransaction()
            .replace(R.id.home_target, homeFragment)
            .commit()
    }

    private fun showHomeFragmentDetail() {
        homeFragmentDetail = HomeFragmentDetail()

        supportActionBar?.title = "Post"
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        supportFragmentManager.beginTransaction()
            .replace(R.id.home_target, homeFragment)
            .commit()
    }

    private fun initNav() {
        //Set current active menu item
        bottom_nav_home.menu.getItem(0).isChecked = true

        //Set routes to other activities
        bottom_nav_home.setOnNavigationItemSelectedListener {
            when (it.toString()) {
                "Home" -> startActivity(Intent(this, HomeActivity::class.java))
                "Categories" -> startActivity(Intent(this, CategoriesActivity::class.java))
                "Notifications" -> startActivity(Intent(this, NotificationActivity::class.java))
                "Settings" -> startActivity(Intent(this, SettingsActivity::class.java))
            }

            overridePendingTransition(0, 0)
            false
        }
    }

}