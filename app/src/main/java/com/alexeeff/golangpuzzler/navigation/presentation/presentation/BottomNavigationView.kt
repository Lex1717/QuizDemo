package com.alexeeff.golangpuzzler.navigation.presentation.presentation

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * @author Yaroslav Alexeev
 */

@StateStrategyType(AddToEndSingleStrategy::class)
interface BottomNavigationView : MvpView {
    fun showTab(position: Int)
}