package com.alexeeff.golangpuzzler.navigation.presentation.common.tabmanager

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment

/**
 * Mediator class between Navigator and Facades of screens
 *
 * @author Yaroslav Alekseev
 */
interface NavigationManager {
    fun getFragmentByKey(screenKey: String, data: Any?) : Fragment?

    fun getActivityIntentByKey(screenKey: String, context: Context, data: Any?) : Intent?
}