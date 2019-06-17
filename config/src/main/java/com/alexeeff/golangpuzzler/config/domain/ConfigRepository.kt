package com.alexeeff.golangpuzzler.config.domain

import com.alexeeff.golangpuzzler.config.domain.entity.UserInfo
import io.reactivex.Single

/**
 * @author Yaroslav Alexeev
 */
interface ConfigRepository {
    fun getUserInfo(): Single<UserInfo>

    fun setUserInfo(userInfo: UserInfo): Single<Boolean>
}