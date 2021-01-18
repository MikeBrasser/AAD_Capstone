package com.example.capstone_forum.notifications

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone_forum.R
import com.example.capstone_forum.categories.CategoriesActivity
import com.example.capstone_forum.home.HomeActivity
import com.example.capstone_forum.home.HomeOverviewFragment
import com.example.capstone_forum.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_categories.*
import kotlinx.android.synthetic.main.activity_notifications.*

class NotificationsActivity : AppCompatActivity() {

    private lateinit var notificationsFragment: NotificationsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        initViews()
        initNav()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_notifications, menu)
        return true
    }

    private fun initViews() {
        showNotificationsFragment()
    }

    fun showNotificationsFragment() {
        notificationsFragment = NotificationsFragment()

        supportActionBar?.title = "Notifications"
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        supportFragmentManager.beginTransaction()
            .replace(R.id.notifications_target, notificationsFragment)
            .commit()
    }

    private fun initNav() {
        //Set current active menu item
        bottom_nav_notifications.menu.getItem(2).isChecked = true

        bottom_nav_notifications.setOnNavigationItemSelectedListener {
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