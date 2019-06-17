package com.alexeeff.golangpuzzler.core.di

import android.content.Context
import android.content.SharedPreferences
import com.alexeeff.golangpuzzler.core.cicerone.LocalCiceroneHolder
import com.alexeeff.golangpuzzler.core.interfaces.NetworkManager
import io.reactivex.Scheduler
import javax.inject.Named

/**
 * @author Yaroslav Alexeev
 */

interface CoreApi {
    fun getNetworkManager(): NetworkManager

    fun getSharedPrefs(): SharedPreferences

    fun localCiceroneHolder(): LocalCiceroneHolder

    fun context(): Context

    @Named(DomainModule.JOB)
    fun jobScheduler(): Scheduler

    @Named(DomainModule.UI)
    fun uiScheduler(): Scheduler
}