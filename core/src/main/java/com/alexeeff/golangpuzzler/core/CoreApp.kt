package com.alexeeff.golangpuzzler.core

import android.app.Application
import com.alexeeff.golangpuzzler.core.di.*

/**
 * Used for getting access to di components throughout the app modules.
 * Application class should implement it.
 *
 * @author Yaroslav Alexeev
 */
abstract class CoreApp : Application() {
        val coreApplicationComponent: CoreApplicationComponent by lazy {
        DaggerCoreComponent.builder()
                .coreModule(CoreModule(this))
                .domainModule(DomainModule())
                .build()
    }

    abstract fun mainApplicationComponent(): FeatureApplicationComponent

    abstract fun puzzlerFeatureComponent(): FeatureApplicationComponent

    abstract fun coursesFeatureComponent(): FeatureApplicationComponent

    abstract fun configFeatureComponent(): FeatureApplicationComponent

    abstract fun profileFeatureComponent(): FeatureApplicationComponent
}