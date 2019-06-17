package com.alexeeff.golangpuzzler.navigation.presentation.common.tabmanager

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import com.alexeeff.golangpuzzler.core.interfaces.ScreenFacade

/**
 * implements {@link NavigatorManager}
 *
 * @author Yaroslav Alekseev
 */

class NavigationManagerImpl(
        private val mainScreenFacade: ScreenFacade,
        private val coursesScreenFacade: ScreenFacade,
        private val profileScreenFacade: ScreenFacade
) : NavigationManager {

    override fun getActivityIntentByKey(
            screenKey: String,
            context: Context,
            data: Any?
    ): Intent? {
        return when {
            mainScreenFacade.isValidScreenKey(screenKey) ->
                mainScreenFacade.getActivityIntent(screenKey, context)
            coursesScreenFacade.isValidScreenKey(screenKey) ->
                coursesScreenFacade.getActivityIntent(screenKey, context, data)
            profileScreenFacade.isValidScreenKey(screenKey) ->
                profileScreenFacade.getActivityIntent(screenKey, context)
            else -> null
        }
    }

    override fun getFragmentByKey(
            screenKey: String,
            data: Any?
    ): Fragment? {
        return when {
            mainScreenFacade.isValidScreenKey(screenKey) ->
                mainScreenFacade.getFragment(screenKey)
            coursesScreenFacade.isValidScreenKey(screenKey) ->
                coursesScreenFacade.getFragment(screenKey, data)
            profileScreenFacade.isValidScreenKey(screenKey) ->
                profileScreenFacade.getFragment(screenKey)
            else -> null
        }
    }
}