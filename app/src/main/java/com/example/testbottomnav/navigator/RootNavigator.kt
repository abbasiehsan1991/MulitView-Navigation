package com.example.testbottomnav.navigator

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class RootNavigator internal constructor(
    fragmentManager: FragmentManager,
    containerId: Int,
    createFragment: (tagId: Int) -> Fragment
) : Navigator(fragmentManager, containerId, createFragment) {

    var currentTag: String? = null
        private set

    var currentFragment: Fragment? = null
        private set

    private val stacks = mutableListOf<StackRecord>()

    fun changeRoot(tagId: Int) {
        val tag = tagId.toString()
        if (currentTag == tag) return
        fragmentManager.run {
            val foundedFragment = findFragmentByTag(tag)
            val fragment = foundedFragment ?: createFragment(tagId)

            beginTransaction()
                .apply {
                    currentTag?.let {
                        findFragmentByTag(it)
                    }?.let {
                        detach(it)
                    }

                    currentTag = tag
                    currentFragment = fragment
                    val record = StackRecord(tag)
                    stacks.remove(record)
                    stacks.add(record)

                    if (foundedFragment != null) {
                        attach(foundedFragment)
                    } else {
                        add(containerId, fragment, tag)
                    }
                }
                .commit()
        }
    }

    fun onBackPressed() {
        fragmentManager.findFragmentById(containerId)?.let {
            if (it.childFragmentManager.backStackEntryCount <= 0) {
                stacks.removeLastOrNull()
                val record = stacks.lastOrNull()
                val fragmentToShow = fragmentManager.findFragmentByTag(record?.tag)

                fragmentManager.beginTransaction()
                    .remove(it)
                    .apply {
                        if (fragmentToShow != null) {
                            attach(fragmentToShow)
                        }
                    }
                    .commit()

                currentFragment = fragmentToShow
                currentTag = fragmentToShow?.tag
            } else {
                it.childFragmentManager.popBackStack()
            }
        }
    }

}