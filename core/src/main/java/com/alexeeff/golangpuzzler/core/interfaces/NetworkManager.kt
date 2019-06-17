package com.alexeeff.golangpuzzler.core.interfaces

/**
 * Checks internet connection
 *
 * @author Yaroslav Alexeev
 */
interface NetworkManager {
    fun isOnline(): Boolean
}