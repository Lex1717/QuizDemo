package com.alexeeff.golangpuzzler.profile.di

import com.alexeeff.golangpuzzler.config.presentation.ConfigurationFacade
import com.alexeeff.golangpuzzler.core.cicerone.LocalCiceroneHolder
import com.alexeeff.golangpuzzler.core.di.DomainModule
import io.reactivex.Scheduler
import javax.inject.Named

interface ProfileDependencies {
    fun localCiceroneHolder(): LocalCiceroneHolder

    fun configFacade(): ConfigurationFacade

    @Named(DomainModule.JOB)
    fun jobScheduler(): Scheduler

    @Named(DomainModule.UI)
    fun uiScheduler(): Scheduler
}