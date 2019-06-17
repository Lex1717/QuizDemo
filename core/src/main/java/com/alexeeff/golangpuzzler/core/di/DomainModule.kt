package com.alexeeff.golangpuzzler.core.di

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named

/**
 * @author Yaroslav Alekseev
 */

@Module
class DomainModule {

    @Provides
    @FeatureScope
    @Named(JOB)
    fun provideJobScheduler(): Scheduler {
        return Schedulers.io()
    }

    @Provides
    @FeatureScope
    @Named(UI)
    fun provideUiScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    @Provides
    @FeatureScope
    @Named(LOCAL)
    fun provideLocalScheduler(): Scheduler {
        return Schedulers.computation()
    }

    companion object {
        const val JOB = "JOB"
        const val UI = "UI"
        const val LOCAL = "LOCAL"
    }
}