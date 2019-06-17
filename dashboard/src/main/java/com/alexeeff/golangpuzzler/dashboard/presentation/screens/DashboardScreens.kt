package com.alexeeff.golangpuzzler.dashboard.presentation.screens

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import com.alexeeff.golangpuzzler.dashboard.presentation.fragment.DashboardFragment

enum class DashboardScreens {
    DASHBOARD {
        override fun createFragment(data: Any?): Fragment? {
            return DashboardFragment.newInstance()
        }

        override fun createIntent(context: Context, data: Any?): Intent? = null
    };

    abstract fun createIntent(context: Context, data: Any?): Intent?

    abstract fun createFragment(data: Any?): Fragment?

    companion object {
        /**
         * Checks whether the module has a screen with required key
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