package com.alexeeff.golangpuzzler.di

import com.alexeeff.golangpuzzler.core.interfaces.ScreenFacade
import com.alexeeff.golangpuzzler.dashboard.presentation.screens.DashboardScreenFacadeImpl
import com.alexeeff.golangpuzzler.navigation.presentation.common.tabmanager.NavigationManager
import com.alexeeff.golangpuzzler.navigation.presentation.common.tabmanager.NavigationManagerImpl
import com.alexeeff.golangpuzzler.profile.presentation.facade.screen.ProfileScreenFacadeImpl
import com.alexeeff.golangpuzzler.puzzler.presentation.facade.screen.PuzzlerScreenFacadeImpl
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Named
import javax.inject.Singleton

/**
 * @author Yaroslav Alexeev
 */
@Module
class NavigationModule {
    private val cicerone: Cicerone<Router> = Cicerone.create()

    @Provides
    @Singleton
    fun provideRouter(): Router = cicerone.router

    @Provides
    @Singleton
    fun provideNavigatorHolder(): NavigatorHolder = cicerone.navigatorHolder

    @Provides
    @Singleton
    fun provideTabManager(
            @Named(MAIN_SCREEN) mainScreenFacade: ScreenFacade,
            @Named(COURSES_SCREEN) coursesScreenFacade: ScreenFacade,
            @Named(PROFILE_SCREEN) profileScreenFacade: ScreenFacade
    ): NavigationManager {
        return NavigationManagerImpl(
                mainScreenFacade,
                coursesScreenFacade,
                profileScreenFacade
        )
    }

    @Provides
    @Singleton
    @Named(MAIN_SCREEN)
    fun provideCoursesScreenFacade(): ScreenFacade {
        return DashboardScreenFacadeImpl()
    }

    @Provides
    @Singleton
    @Named(COURSES_SCREEN)
    fun provideMainScreenFacade(): ScreenFacade {
        return PuzzlerScreenFacadeImpl()
    }

    @Provides
    @Singleton
    @Named(PROFILE_SCREEN)
    fun provideProfileScreenFacade(): ScreenFacade {
        return ProfileScreenFacadeImpl()
    }

    companion object {
        const val MAIN_SCREEN = "main screen"
        const val COURSES_SCREEN = "courses screen"
        const val PROFILE_SCREEN = "profile screen"
    }
}