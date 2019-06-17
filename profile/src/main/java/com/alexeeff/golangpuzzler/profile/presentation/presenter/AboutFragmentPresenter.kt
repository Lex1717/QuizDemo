package com.alexeeff.golangpuzzler.profile.presentation.presenter

import com.alexeeff.golangpuzzler.profile.presentation.view.AboutFragmentView
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.terrakok.cicerone.Router

/**
 * @author Yaroslav Alexeev
 */
@InjectViewState
class AboutFragmentPresenter(
        val router: Router
): MvpPresenter<AboutFragmentView>() {
    fun onBackClicked(): Boolean {
        router.exit()
        return true
    }
}