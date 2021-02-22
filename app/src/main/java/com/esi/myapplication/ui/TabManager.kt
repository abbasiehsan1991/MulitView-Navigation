package com.esi.myapplication.ui

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.esi.myapplication.MainActivity
import com.esi.myapplication.R
import com.esi.myapplication.ui.dashboard.DashboardFragment
import com.esi.myapplication.ui.home.HomeFragment
import com.esi.myapplication.ui.notification.NotificationsFragment

class TabManager private constructor(private val mainActivity: MainActivity) {
    private val tabHistory = TabHistory()
    private var currentTabContainer: Int = 0
    private var currentTabId: Int = 0
    private val defaultFragmentManager: FragmentManager = mainActivity.supportFragmentManager

    private val startDestinations = mapOf(
        R.id.navigation_home to HomeFragment::class,
        R.id.navigation_dashboard to DashboardFragment::class,
        R.id.navigation_notifications to NotificationsFragment::class
    )

    private val homeTabContianer = R.id.fragmentContainerHome
    private val dashboardTabContainer = R.id.fragmentContainerDashboard
    private val notificationsTabContainer = R.id.fragmentContainerNotifications

    private fun fetchCurrentTabController(): FragmentManager {
        return fetchCurrentTabFragment(currentTabId).childFragmentManager
    }

    fun nagivateInTab(fragment: Fragment, tag: String = fragment::class.java.simpleName) {
        fetchCurrentTabController().beginTransaction()
            .addToBackStack(tag)
            .replace(currentTabContainer, fragment, tag)
            .commit()
    }

    fun switchTab(tabId: Int, addToHistory: Boolean = true) {
        currentTabId = tabId
        fetchCurrentTabFragment(tabId).also {
            setupFragment(it, tabId)
        }

        if (addToHistory)
            tabHistory.push(tabId)
    }

    fun onBackPressed() {
        fetchCurrentTabFragment(currentTabId).let { tabFragment ->
            tabFragment.childFragmentManager.backStackEntryCount.also { backStackCount ->
                if (backStackCount <= 1) {
                    if (currentTabId == R.id.navigation_home)
                        mainActivity.finish()
                    else
                        popTabFragments()
                } else {
                    popInnerFragments(tabFragment, backStackCount)
                }
            }
        }
    }

    private fun popTabFragments() {
        defaultFragmentManager.backStackEntryCount.also { backStackCount ->
            if (backStackCount <= 1)
                mainActivity.finish()
            else {
                tabHistory.pop().also { currentTabId ->
                    switchTab(currentTabId, false)
                }
            }
        }
    }

    private fun popInnerFragments(tabFragment: Fragment, backStackCount: Int) {
        tabFragment.childFragmentManager.getBackStackEntryAt(backStackCount - 1).name?.also {
            Log.d(TAG, "popInnerFragments() called $it")
            tabFragment.childFragmentManager.popBackStack(
                it,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
    }

    private fun fetchCurrentTabFragment(tabId: Int): Fragment {
        return when (tabId) {
            R.id.navigation_home -> {
                currentTabContainer = homeTabContianer
                (fetchFragment(R.id.navigation_home) ?: HomeFragment())
            }

            R.id.navigation_dashboard -> {
                currentTabContainer = dashboardTabContainer
                (fetchFragment(R.id.navigation_dashboard)
                    ?: DashboardFragment())
            }

            else -> {
                currentTabContainer = notificationsTabContainer
                (fetchFragment(R.id.navigation_notifications)
                    ?: NotificationsFragment())
            }
        }
    }

    private fun setupFragment(fragment: Fragment, tabId: Int) {
        Log.d(TAG, "setupFragment() called with: fragment = $fragment, tabId = $tabId")
        defaultFragmentManager.beginTransaction()
            .addToBackStack(tabId.toString())
            .replace(R.id.fragmentContainerActiviy, fragment, tabId.toString())
            .commit()
    }

    private fun fetchFragment(tabId: Int): Fragment? {
        defaultFragmentManager.findFragmentByTag(tabId.toString()).apply {
            return this
        }
    }

    companion object {
        private const val TAG = "TabManager"
        private var INSTANCE: TabManager? = null

        fun getInstance(mainActivity: MainActivity): TabManager {
            INSTANCE ?: apply {
                INSTANCE = TabManager(mainActivity)
            }
            return INSTANCE!!
        }
    }
}