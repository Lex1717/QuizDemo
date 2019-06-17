package com.alexeeff.golangpuzzler.profile.presentation.facade.screen

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import com.alexeeff.golangpuzzler.core.interfaces.ScreenFacade

/**
 * @author Yaroslav Alexeev
 */
class ProfileScreenFacadeImpl : ScreenFacade {

    override fun getActivityIntent(
            screenKey: String,
            context: Context,
            data: Any?
    ): Intent? {
        return ProfileScreens.valueOf(screenKey).createIntent(context, data)
    }

    override fun getFragment(
            screenKey: String,
            data: Any?
    ): Fragment? {
        return ProfileScreens.valueOf(screenKey).createFragment(data)
    }

    override fun isValidScreenKey(screenKey: String): Boolean {
        return ProfileScreens.isValidScreenKey(screenKey)
    }
}