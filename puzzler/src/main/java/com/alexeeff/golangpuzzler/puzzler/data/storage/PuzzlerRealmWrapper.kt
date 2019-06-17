package com.alexeeff.golangpuzzler.puzzler.data.storage

import com.alexeeff.golangpuzzler.core.interfaces.RealmWrapper
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.annotations.RealmModule

/**
 * @author Yaroslav Alexeev
 */
class PuzzlerRealmWrapper : RealmWrapper {
    override fun getRealmInstance(): Realm {
        val realmConfig = RealmConfiguration.Builder()
                .name(DB_NAME)
                .modules(PuzzlerRealmModule())
                .schemaVersion(SCHEMA_VERSION)
                .deleteRealmIfMigrationNeeded()
                .build()
        return Realm.getInstance(realmConfig)
    }
    companion object {
        const val DB_NAME = "puzzler.realm.db"
        const val SCHEMA_VERSION = 0L
    }
}

@RealmModule(library = true, allClasses = true)
class PuzzlerRealmModule