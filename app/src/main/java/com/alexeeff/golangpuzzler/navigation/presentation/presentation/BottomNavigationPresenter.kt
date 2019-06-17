package com.alexeeff.golangpuzzler.navigation.presentation.presentation

import com.alexeeff.golangpuzzler.navigation.presentation.common.Screens
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.terrakok.cicerone.Router

/**
 * @author Yaroslav Alexeev
 */

@InjectViewState
class BottomNavigationPresenter(private val router: Router)
    : MvpPresenter<BottomNavigationView>() {

    fun onTabClick(tabPosition: Int) {
        viewState.showTab(tabPosition)
        router.replaceScreen(Screens.getScreenTagFor(tabPosition))
    }

    fun onBackPressed() {
        router.exit()
    }

    fun onExit() {
        router.exit()
    }

}
