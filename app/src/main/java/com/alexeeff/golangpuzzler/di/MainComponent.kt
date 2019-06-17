package com.alexeeff.golangpuzzler.di

import android.app.Activity
import com.alexeeff.golangpuzzler.GolangPuzzlerApp
import com.alexeeff.golangpuzzler.core.di.CoreApi
import com.alexeeff.golangpuzzler.core.di.FeatureApplicationComponent
import com.alexeeff.golangpuzzler.navigation.presentation.ui.activity.BottomBarNavigationActivity
import com.alexeeff.golangpuzzler.navigation.presentation.ui.fragment.TabContainerFragment
import dagger.Component
import javax.inject.Singleton

/**
 * @author Yaroslav Alexeev
 */

@Singleton
@Component(dependencies = [MainDependencies::class], modules = [NavigationModule::class])
interface MainComponent : FeatureApplicationComponent {

    fun inject(activity: BottomBarNavigationActivity)

    fun inject(fragment: TabContainerFragment)

    companion object {
        fun create(mainDependencies: MainDependencies): MainComponent =
                DaggerMainComponent.builder()
                        .mainDependencies(mainDependencies)
                        .build()
    }
}

@Singleton
@Component(dependencies = [CoreApi::class])
interface MainDependenciesComponent: MainDependencies

internal fun Activity.getComponent() =
        (application as GolangPuzzlerApp).mainApplicationComponent() as MainComponent