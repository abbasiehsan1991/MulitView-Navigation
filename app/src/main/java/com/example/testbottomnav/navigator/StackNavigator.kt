package com.example.testbottomnav.navigator

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class StackNavigator internal constructor(
    fragmentManager: FragmentManager,
    containerId: Int,
    createFragment: (tagId: Int) -> Fragment
) : Navigator(fragmentManager, containerId, createFragment) {

    fun pushFragment(tagId: Int) {
        val tag = tagId.toString()
        fragmentManager.run {
            takeIf {
                findFragmentByTag(tag) == null
            }?.apply {
                beginTransaction()
                    .replace(containerId, createFragment(tagId), tag)
                    .addToBackStack(tag)
                    .commit()
            }
        }
    }

}

