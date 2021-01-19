package com.example.capstone_forum.settings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone_forum.R
import com.example.capstone_forum.categories.CategoriesActivity
import com.example.capstone_forum.home.HomeActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    private lateinit var settingsOverviewFragment: SettingsOverviewFragment
    private lateinit var settingsInfoFragment: SettingsInfoFragment

    companion object {
        const val SETTINGS_OVERVIEW = "SETTINGS_OVERVIEW"
        const val SETTINGS_INFO = "SETTINGS_INFO"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        initViews()
        initNav()
    }

    private fun initViews() {
        settingsOverviewFragment = SettingsOverviewFragment()

        supportActionBar?.title = "Settings"
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.settings_target, settingsOverviewFragment, SETTINGS_OVERVIEW)
            .commit()
    }

    fun showInfoFragment() {
        settingsInfoFragment = SettingsInfoFragment()

        supportActionBar?.title = "About the app"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.settings_target, settingsInfoFragment, SETTINGS_INFO)
            .addToBackStack(SETTINGS_OVERVIEW)
            .commit()
    }

    private fun initNav() {
        //Set current active menu item
        bottom_nav_settings.menu.getItem(2).isChecked = true

        bottom_nav_settings.setOnNavigationItemSelectedListener {
            when (it.toString()) {
                "Home" -> startActivity(Intent(this, HomeActivity::class.java))
                "Categories" -> startActivity(Intent(this, CategoriesActivity::class.java))
                "Settings" -> startActivity(Intent(this, SettingsActivity::class.java))
            }

            overridePendingTransition(0, 0)
            false
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        initViews()
        return true
    }
}