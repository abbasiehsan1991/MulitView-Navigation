package com.esi.myapplication.ui

class TabHistory {

    private val stack = mutableListOf<Int>()

    fun push(id: Int) {
        stack.add(id)
    }
}