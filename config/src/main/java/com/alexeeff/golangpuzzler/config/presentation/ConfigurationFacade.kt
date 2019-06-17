package com.alexeeff.golangpuzzler.config.presentation

import com.alexeeff.golangpuzzler.config.domain.entity.UserInfo
import io.reactivex.Single

/**
 * Facade for interaction with config module
 *
 * @author Yaroslav Alexeev
 */
interface ConfigurationFacade {
    fun setUserInfo(userInfo: UserInfo): Single<Boolean>

    fun getUserInfo(): Single<UserInfo>
}