package com.alexeeff.golangpuzzler.config.data.storage

import com.alexeeff.golangpuzzler.config.data.storage.objects.UserInfoStoredObject
import com.alexeeff.golangpuzzler.config.domain.entity.UserInfo
import com.alexeeff.golangpuzzler.core.interfaces.RealmWrapper
import io.realm.Realm
import io.realm.exceptions.RealmFileException
import io.realm.kotlin.where

/**
 * @author Yaroslav Alekseev
 */
class ConfigurationStorageImpl(
        private val realmWrapper: RealmWrapper
) : ConfigurationStorage {

    override fun getUserInfo(): UserInfo {
        val realm = realmWrapper.getRealmInstance()
        val result =
                realm.where<UserInfoStoredObject>()
                        .findFirst()

        return result.mapToEntity()
    }

    override fun setUserInfo(userInfo: UserInfo): Boolean {
        val realm = realmWrapper.getRealmInstance()

        return try {
            realm.beginTransaction()
            updateOrInsertUserInfo(userInfo, realm)
            realm.commitTransaction()
            true
        } catch (e: RealmFileException) {
            if (realm.isInTransaction) {
                realm.cancelTransaction()
            }
            false
        } finally {
            realm.close()
        }
    }

    private fun updateOrInsertUserInfo(
            userInfo: UserInfo,
            realm: Realm
    ) {
        val userInfoStoredObject = userInfo.mapToStoredObject(DEFAULT_INFO_ID)
        realm.copyToRealmOrUpdate(userInfoStoredObject)
    }

    companion object {
        // Default id for simplifying updating or inserting userInfo in this demo,
        // 'cause we have only one object of this class to store
        const val DEFAULT_INFO_ID = 0
    }
}