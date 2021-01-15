package com.example.capstone_forum.notifications

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone_forum.R
import com.example.capstone_forum.categories.CategoriesActivity
import com.example.capstone_forum.home.HomeActivity
import com.example.capstone_forum.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_categories.*
import kotlinx.android.synthetic.main.activity_notifications.*

class NotificationsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        initNav()
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