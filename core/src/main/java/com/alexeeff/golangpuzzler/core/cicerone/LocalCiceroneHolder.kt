package com.alexeeff.golangpuzzler.core.cicerone

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import java.util.*

/**
 * Implements {@link LocalCicerone}
 *
 * @author Yaroslav Alexeev
 */
class LocalCiceroneHolder: LocalCicerone {
    private var containers: HashMap<String, Cicerone<Router>> = HashMap()

    fun getMainCicerone(): Cicerone<Router> {
        return getCicerone(MAIN)
    }

    fun getCoursesCicerone(): Cicerone<Router> {
        return getCicerone(COURSES)
    }

    fun getProfileCicerone(): Cicerone<Router> {
        return getCicerone(PROFILE)
    }


    override fun getCicerone(containerTag: String): Cicerone<Router> {
        if (!containers.containsKey(containerTag)) {
            containers[containerTag] = Cicerone.create()
        }
        return containers[containerTag]!!
    }

    companion object {
        private const val MAIN = "main"
        private const val COURSES = "courses"
        private const val PROFILE = "profile"
    }
}