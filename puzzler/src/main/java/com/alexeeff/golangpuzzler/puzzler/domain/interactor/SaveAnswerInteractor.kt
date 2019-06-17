package com.alexeeff.golangpuzzler.puzzler.domain.interactor

import com.alexeeff.golangpuzzler.config.domain.entity.UserInfo
import com.alexeeff.golangpuzzler.config.presentation.ConfigurationFacade
import io.reactivex.Scheduler
import io.reactivex.Single

class SaveAnswerInteractor(
        private val jobScheduler: Scheduler,
        private val uiScheduler: Scheduler,
        private val configurationFacade: ConfigurationFacade
) {

    fun saveAnswer(isCorrect: Boolean): Single<Boolean> {
        return configurationFacade.getUserInfo()
                .map { userInfo: UserInfo ->
                    val correctAnswers = if (isCorrect) {
                        userInfo.correctAnswerCount + 1
                    } else {
                        userInfo.correctAnswerCount
                    }

                    UserInfo(
                            userInfo.questionShowed + 1,
                            userInfo.questionAnswered + 1,
                            correctAnswers
                    )
                }
                .flatMap { userInfo ->
                    configurationFacade.setUserInfo(userInfo)
                }
                .subscribeOn(jobScheduler)
                .observeOn(uiScheduler)
    }
}