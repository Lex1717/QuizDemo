package com.alexeeff.golangpuzzler.core.interfaces

import io.realm.Realm

/**
 * Wrapper for getting configured version of the realm db
 *
 * @author Yaroslav Alexeev
 */
interface RealmWrapper {
    fun getRealmInstance(): Realm
}