package com.alexeeff.golangpuzzler.profile.domain

import com.alexeeff.golangpuzzler.config.domain.entity.UserInfo
import com.alexeeff.golangpuzzler.config.presentation.ConfigurationFacade
import io.reactivex.Scheduler
import io.reactivex.Single

/**
 * @author Yaroslav Alexeev
 */
class GetUserResultInteractor(
        private val jobScheduler: Scheduler,
        private val uiScheduler: Scheduler,
        private val configFacade: ConfigurationFacade
) {

    fun execute(): Single<UserInfo> {
        return configFacade.getUserInfo()
                .subscribeOn(jobScheduler)
                .observeOn(uiScheduler)
    }
}