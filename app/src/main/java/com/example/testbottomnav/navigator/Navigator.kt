package com.example.testbottomnav.navigator

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

abstract class Navigator(
    protected val fragmentManager: FragmentManager,
    protected val containerId: Int,
    protected val createFragment: (tagId: Int) -> Fragment
)