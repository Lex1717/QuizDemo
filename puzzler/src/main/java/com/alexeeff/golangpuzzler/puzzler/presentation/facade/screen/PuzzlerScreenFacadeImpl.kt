package com.alexeeff.golangpuzzler.puzzler.presentation.facade.screen

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import com.alexeeff.golangpuzzler.core.interfaces.ScreenFacade

/**
 * @author Yaroslav Alexeev
 */
class PuzzlerScreenFacadeImpl : ScreenFacade {

    override fun getActivityIntent(screenKey: String, context: Context, data: Any?): Intent? {
        return PuzzlerScreens.valueOf(screenKey).createIntent(context, data)
    }

    override fun getFragment(screenKey: String, data: Any?): Fragment? {
        return PuzzlerScreens.valueOf(screenKey).createFragment(data)
    }

    override fun isValidScreenKey(screenKey: String): Boolean {
        return PuzzlerScreens.isValidScreenKey(screenKey)
    }
}