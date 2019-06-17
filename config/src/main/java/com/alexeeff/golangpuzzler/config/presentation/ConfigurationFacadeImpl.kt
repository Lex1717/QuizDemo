package com.alexeeff.golangpuzzler.config.presentation

import com.alexeeff.golangpuzzler.config.domain.entity.UserInfo
import com.alexeeff.golangpuzzler.config.domain.interactor.GetUserInfoInteractor
import com.alexeeff.golangpuzzler.config.domain.interactor.SetUserInfoInteractor
import io.reactivex.Single

/**
 * @author Yaroslav Alexeev
 */
class ConfigurationFacadeImpl(
        private val setUserInfoInteractor: SetUserInfoInteractor,
        private val getUserInfoInteractor: GetUserInfoInteractor
) : ConfigurationFacade {

    override fun setUserInfo(userInfo: UserInfo): Single<Boolean> {
        return setUserInfoInteractor.execute(userInfo)
    }

    override fun getUserInfo(): Single<UserInfo> {
        return getUserInfoInteractor.execute()
    }
}