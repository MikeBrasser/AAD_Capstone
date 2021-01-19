package com.example.capstone_forum

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone_forum.categories.CategoriesActivity
import com.example.capstone_forum.home.HomeActivity
import com.example.capstone_forum.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        initNav()
    }

    private fun initNav() {
        //Set current active menu item
        bottom_navigation_menu.menu.getItem(0).isChecked = true

        bottom_navigation_menu.setOnNavigationItemSelectedListener {
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