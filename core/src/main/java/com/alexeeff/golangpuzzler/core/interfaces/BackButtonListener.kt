package com.alexeeff.golangpuzzler.core.interfaces

/**
 * All screens that want to intercept back button press event, should implement this interface
 *
 * @author Yaroslav Alexeev
 */
interface BackButtonListener {
    fun onBackPressed(): Boolean
}