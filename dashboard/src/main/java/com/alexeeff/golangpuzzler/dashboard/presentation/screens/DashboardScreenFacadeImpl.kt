package com.alexeeff.golangpuzzler.dashboard.presentation.screens

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import com.alexeeff.golangpuzzler.core.interfaces.ScreenFacade

/**
 * {@implements ScreenFacade}
 *
 * @author Yaroslav Alexeev
 */
class DashboardScreenFacadeImpl : ScreenFacade {

    override fun getActivityIntent(screenKey: String, context: Context, data: Any?): Intent? {
        return DashboardScreens.valueOf(screenKey).createIntent(context, data)
    }

    override fun getFragment(screenKey: String, data: Any?): Fragment? {
        return DashboardScreens.valueOf(screenKey).createFragment(data)
    }

    override fun isValidScreenKey(screenKey: String): Boolean {
        return DashboardScreens.isValidScreenKey(screenKey)
    }
}