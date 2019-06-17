package com.alexeeff.golangpuzzler.config.data

import com.alexeeff.golangpuzzler.config.data.storage.ConfigurationStorage
import com.alexeeff.golangpuzzler.config.domain.ConfigRepository
import com.alexeeff.golangpuzzler.config.domain.entity.UserInfo
import io.reactivex.Single

/**
 * @author Yaroslav Alexeev
 */
class ConfigRepositoryImpl(private val configurationStorage: ConfigurationStorage) : ConfigRepository {
    override fun getUserInfo(): Single<UserInfo> {
        return Single
                .fromCallable {
                    configurationStorage.getUserInfo()
                }
    }

    override fun setUserInfo(userInfo: UserInfo): Single<Boolean> {
        return Single
                .just(userInfo)
                .map { info -> configurationStorage.setUserInfo(info) }
    }
}