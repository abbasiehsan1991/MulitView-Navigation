package com.esi.myapplication.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.esi.myapplication.R
import com.esi.myapplication.ui.dashboard.DashboardFragment
import com.esi.myapplication.ui.home.HomeFragment
import com.esi.myapplication.ui.notification.NotificationsFragment

class TabManager private constructor(private val defaultFragmentManager: FragmentManager) {
    private val tabHistory = TabHistory().apply { push(R.id.navigation_home) }
    private var currentTabContainer: Int = 0
    private var currentTabId: Int = 0

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
            setupFragment(it)
        }

        if (addToHistory)
            tabHistory.push(tabId)
    }

    fun onBackPressed() {
        fetchCurrentTabFragment(currentTabId).let { tabFragment ->
            tabFragment.childFragmentManager.backStackEntryCount.also { backStackCount ->
                if (backStackCount == 0)
                    return
            }.also { backStackCount ->
                tabFragment.childFragmentManager.getBackStackEntryAt(backStackCount - 1).name?.also {
                    tabFragment.childFragmentManager.popBackStack(
                        it,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                }
            }
        }
    }

    private fun fetchCurrentTabFragment(tabId: Int): Fragment {
        return when (tabId) {
            R.id.navigation_home -> {
                currentTabContainer = homeTabContianer
                (fetchFragment(HomeFragment::class.java.simpleName) ?: HomeFragment())
            }

            R.id.navigation_dashboard -> {
                currentTabContainer = dashboardTabContainer
                (fetchFragment(DashboardFragment::class.java.simpleName)
                    ?: DashboardFragment())
            }

            else -> {
                currentTabContainer = notificationsTabContainer
                (fetchFragment(NotificationsFragment::class.java.simpleName)
                    ?: NotificationsFragment())
            }
        }
    }

    private fun setupFragment(fragment: Fragment) {
        defaultFragmentManager.beginTransaction()
            .addToBackStack(fragment::class.java.simpleName)
            .replace(R.id.fragmentContainerActiviy, fragment, fragment::class.java.simpleName)
            .commit()
    }

    private fun fetchFragment(fragmentName: String): Fragment? {
        defaultFragmentManager.findFragmentByTag(fragmentName).apply {
            return this
        }
    }

    companion object {
        private const val TAG = "TabManager"
        private var INSTANCE: TabManager? = null

        fun getInstance(defaultFragmentManager: FragmentManager): TabManager {
            INSTANCE ?: apply {
                INSTANCE = TabManager(defaultFragmentManager)
            }
            return INSTANCE!!
        }
    }
}