package com.alexeeff.golangpuzzler.core.interfaces

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment

/**
 * Helps to get fragments and activity's intents from other modules.
 * Used for encapsulating navigation data in modules.
 *
 * @author Yaroslav Alexeev
 */
interface ScreenFacade {

    fun getFragment(screenKey: String, data: Any? = null) : Fragment?

    fun getActivityIntent(screenKey: String, context: Context, data: Any? = null): Intent?

    /**
     * Checks whether it possible to create an activity or a fragment by a key.
     * @param screenKey key, that should be tested
     */
    fun isValidScreenKey(screenKey: String): Boolean
}