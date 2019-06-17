package com.alexeeff.golangpuzzler.core.di

import dagger.Component

/**
 * @author Yaroslav Alexeev
 */

@FeatureScope
@Component(modules = [CoreModule::class, DomainModule::class])
interface CoreComponent : CoreApi, CoreApplicationComponent {

    companion object {
        fun create(): CoreComponent =
                DaggerCoreComponent.builder()
                        .build()
    }
}
