package com.alexeeff.golangpuzzler.puzzler.di

import android.content.Context
import com.alexeeff.golangpuzzler.config.presentation.ConfigurationFacade
import com.alexeeff.golangpuzzler.core.cicerone.LocalCiceroneHolder
import com.alexeeff.golangpuzzler.core.di.DomainModule
import com.alexeeff.golangpuzzler.core.interfaces.NetworkManager
import io.reactivex.Scheduler
import javax.inject.Named

interface PuzzlerDependencies {
    fun localCiceroneHolder(): LocalCiceroneHolder

    fun configFacade(): ConfigurationFacade

    fun context(): Context

    fun networkManager(): NetworkManager

    @Named(DomainModule.JOB)
    fun jobScheduler(): Scheduler

    @Named(DomainModule.UI)
    fun uiScheduler(): Scheduler
}