package com.alexeeff.golangpuzzler.config.domain.interactor

import com.alexeeff.golangpuzzler.config.domain.ConfigRepository
import com.alexeeff.golangpuzzler.config.domain.entity.UserInfo
import io.reactivex.Single

/**
 * @author Yaroslav Alexeev
 */
class GetUserInfoInteractor(
        private val configRepository: ConfigRepository
) {

    fun execute(): Single<UserInfo> {
        return configRepository.getUserInfo()
    }

}