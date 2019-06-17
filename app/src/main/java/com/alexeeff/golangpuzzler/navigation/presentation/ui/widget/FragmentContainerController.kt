package com.alexeeff.golangpuzzler.navigation.presentation.ui.widget

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.alexeeff.golangpuzzler.R
import com.alexeeff.golangpuzzler.navigation.presentation.common.Screens
import com.alexeeff.golangpuzzler.navigation.presentation.ui.fragment.TabContainerFragment

/**
 * @author Yaroslav Alexeev
 */

class FragmentContainerController(
        private val fm: FragmentManager
) {
    private var mainFragment: Fragment? = null
    private var coursesFragment: Fragment? = null
    private var profileFragment: Fragment? = null

    init {
        initMainFragment()

        initCoursesFragment()

        initProfileFragment()
    }

    private fun initMainFragment() {
        //At the first time, shows mainFragment
        mainFragment = fm.findFragmentByTag(Screens.MAIN.toString())
        if (mainFragment == null) {
            mainFragment = TabContainerFragment.newInstance(Screens.MAIN.toString())

            fm.beginTransaction()
                    .add(
                            R.id.container,
                            mainFragment!!,
                            Screens.MAIN.toString()
                    )
                    .commitNow()
        }
    }

    private fun initCoursesFragment() {
        coursesFragment = fm.findFragmentByTag(Screens.COURSES.toString())
        if (coursesFragment == null) {
            coursesFragment = TabContainerFragment.newInstance(Screens.COURSES.toString())

            fm.beginTransaction()
                    .add(
                            R.id.container,
                            coursesFragment!!,
                            Screens.COURSES.toString()
                    )
                    .detach(coursesFragment!!)
                    .commitNow()
        }
    }

    private fun initProfileFragment() {
        profileFragment = fm.findFragmentByTag(Screens.PROFILE.toString())
        if (profileFragment == null) {
            profileFragment = TabContainerFragment.newInstance(Screens.PROFILE.toString())

            fm.beginTransaction()
                    .add(
                            R.id.container,
                            profileFragment!!,
                            Screens.PROFILE.toString()
                    )
                    .detach(profileFragment!!)
                    .commitNow()
        }
    }

    fun openFragment(screenKey: String) {
        when (screenKey) {
            Screens.MAIN.toString() -> openMainFragment()
            Screens.COURSES.toString() -> openCoursesFragment()
            Screens.PROFILE.toString() -> openProfileFragment()
        }
    }

    private fun openMainFragment() {
        fm.beginTransaction()
                .attach(mainFragment!!)
                .detach(profileFragment!!)
                .detach(coursesFragment!!)
                .commitNow()
    }

    private fun openCoursesFragment() {
        fm.beginTransaction()
                .detach(mainFragment!!)
                .detach(profileFragment!!)
                .attach(coursesFragment!!)
                .commitNow()
    }

    private fun openProfileFragment() {
        fm.beginTransaction()
                .detach(coursesFragment!!)
                .attach(profileFragment!!)
                .detach(mainFragment!!)
                .commitNow()
    }
}