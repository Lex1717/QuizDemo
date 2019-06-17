package com.alexeeff.golangpuzzler.profile.presentation.presenter

import com.alexeeff.golangpuzzler.profile.presentation.facade.screen.ProfileScreens
import com.alexeeff.golangpuzzler.profile.presentation.view.ProfileFragmentView
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.terrakok.cicerone.Router

/**
 * @author Yaroslav Alexeev
 */
@InjectViewState
class ProfileFragmentPresenter(
        val router: Router
): MvpPresenter<ProfileFragmentView>() {

    fun onUsersResultClicked() {
        router.navigateTo(ProfileScreens.RESULTS.toString())
    }

    fun onAboutClicked() {
        router.navigateTo(ProfileScreens.ABOUT.toString())
    }
}