package com.alexeeff.golangpuzzler.navigation.presentation.common

import com.alexeeff.golangpuzzler.core.cicerone.LocalCiceroneHolder
import com.alexeeff.golangpuzzler.dashboard.presentation.screens.DashboardScreens
import com.alexeeff.golangpuzzler.profile.presentation.facade.screen.ProfileScreens
import com.alexeeff.golangpuzzler.puzzler.presentation.facade.screen.PuzzlerScreens
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

/**
 * A list of screens available from navigationBar
 * Every screen must have initial tag of a fragment, that will be opened on screen's start.
 *
 * @author Yaroslav Alekseev
 */

enum class Screens(val initialFragmentTag: String) {
    MAIN(DashboardScreens.DASHBOARD.toString()) {
        override fun getCicerone(ciceroneHolder: LocalCiceroneHolder): Cicerone<Router> {
            return ciceroneHolder.getMainCicerone()
        }
    },
    COURSES(PuzzlerScreens.COURSE_LIST.toString()) {
        override fun getCicerone(ciceroneHolder: LocalCiceroneHolder): Cicerone<Router> {
            return ciceroneHolder.getCoursesCicerone()
        }
    },
    PROFILE(ProfileScreens.PROFILE.toString()) {
        override fun getCicerone(ciceroneHolder: LocalCiceroneHolder): Cicerone<Router> {
            return ciceroneHolder.getProfileCicerone()
        }
    };

    abstract fun getCicerone(ciceroneHolder: LocalCiceroneHolder): Cicerone<Router>

    companion object {
        fun getScreenTagFor(position: Int): String {
            return when (position) {
                0 -> MAIN.toString()
                1 -> COURSES.toString()
                2 -> PROFILE.toString()
                else -> ""
            }
        }
    }
}