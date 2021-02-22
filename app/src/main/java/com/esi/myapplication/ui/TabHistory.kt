package com.esi.myapplication.ui

class TabHistory {

    private val stack = mutableListOf<Int>()
    private val isStackEmpty: Boolean
        get() = stack.size <= 1

    fun push(id: Int) {
        stack.add(id)
    }

    fun pop(): Int {
        var currentTopTabID = -1
        if (!isStackEmpty) {
            currentTopTabID = stack[stack.size - 2]
            stack.removeAt(stack.size - 1)
        }
        return currentTopTabID
    }
}