package com.alexeeff.golangpuzzler.profile.presentation.view

import com.alexeeff.golangpuzzler.config.domain.entity.UserInfo
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface ResultsFragmentView: MvpView {
    fun showResults(userInfo: UserInfo)
}