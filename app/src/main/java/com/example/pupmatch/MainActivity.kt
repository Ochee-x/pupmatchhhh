package com.example.finalapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                R.id.bottom_message -> {
                    replaceFragment(MessageFragment())
                    true
                }
                R.id.bottom_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.bottom_matched -> {
                    replaceFragment(MatchedFragment())
                    true
                }
                R.id.bottom_notification -> {
                    replaceFragment(NotifFragment())
                    true
                }
                else -> false
            }
        }

        // Load the default fragment
        replaceFragment(ProfileFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, fragment)
            .commit()
    }
}
