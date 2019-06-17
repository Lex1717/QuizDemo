package com.alexeeff.golangpuzzler.puzzler.presentation.facade.screen

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import com.alexeeff.golangpuzzler.puzzler.presentation.ui.fragment.CourseListFragment
import com.alexeeff.golangpuzzler.puzzler.presentation.ui.fragment.PuzzlerFragment
import com.alexeeff.golangpuzzler.puzzler.presentation.ui.fragment.ResultFragment

/**
 * @author Yaroslav Alexeev
 */
enum class PuzzlerScreens {
    PUZZLER {
        override fun createFragment(data: Any?): Fragment? {
            return PuzzlerFragment.newInstance(data as Int)
        }

        override fun createIntent(context: Context, data: Any?): Intent? = null
    },
    RESULT{
        override fun createIntent(context: Context, data: Any?): Intent? = null

        override fun createFragment(data: Any?): Fragment? {
            return ResultFragment.newInstance()
        }
    },
    COURSE_LIST{
        override fun createIntent(context: Context, data: Any?): Intent? = null

        override fun createFragment(data: Any?): Fragment? {
            return CourseListFragment.newInstance()
        }
    };

    abstract fun createIntent(context: Context, data: Any?): Intent?

    abstract fun createFragment(data: Any?): Fragment?

    companion object {
        /**
         * Checks whether the module has a screen with this screenKey
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