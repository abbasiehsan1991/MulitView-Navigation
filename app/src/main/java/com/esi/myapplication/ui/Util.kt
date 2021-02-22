package com.esi.myapplication.ui

import android.util.Log
import androidx.fragment.app.FragmentManager

fun printBackStack(supportFragmentManager: FragmentManager) {
    for (entry in 0 until supportFragmentManager.backStackEntryCount) {
        Log.i(
            "STACK-PRINT",
            "Found fragment: ${supportFragmentManager.getBackStackEntryAt(entry).id}:${
                supportFragmentManager.getBackStackEntryAt(entry).name
            }"
        )
    }
}
