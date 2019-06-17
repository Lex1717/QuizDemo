package com.alexeeff.golangpuzzler.config.di

import com.alexeeff.golangpuzzler.core.di.FeatureApplicationComponent
import com.alexeeff.golangpuzzler.core.di.FeatureScope
import dagger.Component

@FeatureScope
@Component(modules = [ConfigModule::class])
interface ConfigComponent : ConfigApi, FeatureApplicationComponent {

    companion object {
        fun create(): ConfigComponent =
                DaggerConfigComponent.builder()
                        .build()
    }
}