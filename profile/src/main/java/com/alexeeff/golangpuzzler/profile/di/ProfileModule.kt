package com.alexeeff.golangpuzzler.profile.di

import com.alexeeff.golangpuzzler.config.presentation.ConfigurationFacade
import com.alexeeff.golangpuzzler.core.cicerone.LocalCiceroneHolder
import com.alexeeff.golangpuzzler.core.di.DomainModule
import com.alexeeff.golangpuzzler.profile.domain.GetUserResultInteractor
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import ru.terrakok.cicerone.Router
import javax.inject.Named

@Module
class ProfileModule {
    @Provides
    fun provideRouter(ciceroneHolder: LocalCiceroneHolder): Router {
        return ciceroneHolder.getProfileCicerone().router
    }

    @Provides
    fun provideGetUserResultInteractor(
            @Named(DomainModule.JOB) jobScheduler: Scheduler,
            @Named(DomainModule.UI) uiScheduler: Scheduler,
            configurationFacade: ConfigurationFacade
    ): GetUserResultInteractor {
        return GetUserResultInteractor(jobScheduler, uiScheduler, configurationFacade)
    }
}