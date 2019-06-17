package com.alexeeff.golangpuzzler.config.domain.interactor

import com.alexeeff.golangpuzzler.config.domain.ConfigRepository
import com.alexeeff.golangpuzzler.config.domain.entity.UserInfo
import io.reactivex.Single

/**
 * @author Yaroslav Alexeev
 */
class SetUserInfoInteractor(
        private val configRepository: ConfigRepository
) {

    fun execute(userInfo: UserInfo): Single<Boolean> {
        return configRepository.setUserInfo(userInfo)
    }

}