package com.alexeeff.golangpuzzler.di

import android.content.SharedPreferences
import com.alexeeff.golangpuzzler.core.cicerone.LocalCiceroneHolder
import com.alexeeff.golangpuzzler.core.di.DomainModule
import io.reactivex.Scheduler
import javax.inject.Named

/**
 * @author Yaroslav Alexeev
 */

interface MainDependencies {

    fun getSharedPrefs(): SharedPreferences

    fun localCiceroneHolder(): LocalCiceroneHolder

    @Named(DomainModule.JOB)
    fun jobScheduler(): Scheduler

    @Named(DomainModule.UI)
    fun uiScheduler(): Scheduler
}