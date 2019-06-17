package com.alexeeff.golangpuzzler.config.data.storage

import com.alexeeff.golangpuzzler.core.interfaces.RealmWrapper
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.annotations.RealmModule

/**
 * Wrapper for getting a realmInstance
 *
 * @author Yaroslav Alexeev
 */
class ConfigRealmWrapper : RealmWrapper {
    override fun getRealmInstance(): Realm {
        val realmConfig = RealmConfiguration.Builder()
                .name(DB_NAME)
                .modules(ConfigRealmModule())
                .schemaVersion(SCHEMA_VERSION)
                .deleteRealmIfMigrationNeeded()
                .build()
        return Realm.getInstance(realmConfig)
    }

    companion object {
        const val DB_NAME = "com.alexeeff.golangpuzzler.config.realm.db"
        const val SCHEMA_VERSION = 0L
    }
}

@RealmModule(library = true, allClasses = true)
class ConfigRealmModule