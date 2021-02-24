package com.esi.myapplication

import androidx.fragment.app.Fragment

interface Navigator {
    fun navigateInTab(
        fragment: Fragment,
        tag: String = fragment::class.java.simpleName
    )
}
