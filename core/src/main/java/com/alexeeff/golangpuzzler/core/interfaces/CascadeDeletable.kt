package com.alexeeff.golangpuzzler.core.interfaces

/**
 * All objects that contain realmLists should implement this interface
 *
 * @author Yaroslav Alexeev
 */
interface CascadeDeletable {
    fun cascadeDelete()
}