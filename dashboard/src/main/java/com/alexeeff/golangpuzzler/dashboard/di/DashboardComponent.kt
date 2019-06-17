package com.alexeeff.golangpuzzler.dashboard.di

import com.alexeeff.golangpuzzler.config.di.ConfigComponent
import com.alexeeff.golangpuzzler.core.di.FeatureApplicationComponent
import dagger.Component

@DashboardScope
@Component(dependencies = [ConfigComponent::class], modules = [DashboardModule::class])
interface DashboardComponent : FeatureApplicationComponent {

    companion object {
        fun create(configComponent: FeatureApplicationComponent): DashboardComponent =
                DaggerDashboardComponent.builder()
                        .configComponent(configComponent as ConfigComponent)
                        .build()
    }
}