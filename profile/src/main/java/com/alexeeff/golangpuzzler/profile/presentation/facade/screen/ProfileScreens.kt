package com.alexeeff.golangpuzzler.profile.presentation.facade.screen

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import com.alexeeff.golangpuzzler.profile.presentation.ui.fragment.AboutFragment
import com.alexeeff.golangpuzzler.profile.presentation.ui.fragment.ProfileFragment
import com.alexeeff.golangpuzzler.profile.presentation.ui.fragment.UserResultsFragment

/**
 * List of the screens of the profile module
 */
enum class ProfileScreens {
    PROFILE {
        override fun createFragment(data: Any?): Fragment? {
            return ProfileFragment.newInstance()
        }

        override fun createIntent(context: Context, data: Any?): Intent? = null
    },
    ABOUT {
        override fun createFragment(data: Any?): Fragment? {
            return AboutFragment.newInstance()
        }

        override fun createIntent(context: Context, data: Any?): Intent? = null
    },
    RESULTS {
        override fun createFragment(data: Any?): Fragment? {
            return UserResultsFragment.newInstance()
        }

        override fun createIntent(context: Context, data: Any?): Intent? = null
    };

    abstract fun createIntent(context: Context, data: Any?): Intent?

    abstract fun createFragment(data: Any?): Fragment?

    companion object {
        /**
         * Checks whether the module has required screen
         */
        fun isValidScreenKey(screenKey: String): Boolean {
            return screensSet.contains(screenKey)
        }

        private val screensSet by lazy {
            val set = HashSet<String>()
            set.addAll(values().mapTo(ArrayList()){it.toString()})
            set
        }
    }
}