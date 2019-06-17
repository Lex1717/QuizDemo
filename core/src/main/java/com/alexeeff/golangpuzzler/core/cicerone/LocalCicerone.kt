package com.alexeeff.golangpuzzler.core.cicerone

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

/**
 * Holds routers for nested tabs' navigation
 *
 * @author Yaroslav Alexeev
 */
interface LocalCicerone {
    fun getCicerone(containerTag: String): Cicerone<Router>
}