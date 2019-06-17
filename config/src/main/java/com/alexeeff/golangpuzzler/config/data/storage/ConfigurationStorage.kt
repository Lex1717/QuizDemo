package com.alexeeff.golangpuzzler.config.data.storage

import com.alexeeff.golangpuzzler.config.domain.entity.UserInfo

/**
 * @author Yaroslav Alekseev
 */
interface ConfigurationStorage {
    fun getUserInfo(): UserInfo

    fun setUserInfo(userInfo: UserInfo): Boolean
}