package com.example.testbottomnav

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.testbottomnav.navigator.RootNavigator
import com.example.testbottomnav.ui.dashboard.DashboardFragment
import com.example.testbottomnav.ui.home.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val rootNavigator by lazy {
        RootNavigator(supportFragmentManager, R.id.nav_host_fragment, ::createFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        rootNavigator.changeRoot(R.id.navigation_home)

        navView.setOnNavigationItemSelectedListener {
            rootNavigator.changeRoot(it.itemId)
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun onBackPressed() {
        if (
            rootNavigator.currentTag != R.id.navigation_home.toString() ||
            rootNavigator.currentFragment?.childFragmentManager?.backStackEntryCount.let {
                it ?: 0
            } > 0
        ) {
            rootNavigator.onBackPressed()
        } else {
            finish()
        }
    }

    private fun createFragment(id: Int): Fragment {
        return when (id) {
            R.id.navigation_home -> HomeFragment()
            R.id.navigation_dashboard -> DashboardFragment()
            else -> TODO("Not yet implemented")
        }
    }

}