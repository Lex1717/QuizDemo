package com.alexeeff.golangpuzzler.profile.di

import android.app.Activity
import com.alexeeff.golangpuzzler.config.di.ConfigApi
import com.alexeeff.golangpuzzler.core.CoreApp
import com.alexeeff.golangpuzzler.core.di.CoreApi
import com.alexeeff.golangpuzzler.core.di.FeatureApplicationComponent
import com.alexeeff.golangpuzzler.core.di.FeatureScope
import com.alexeeff.golangpuzzler.profile.presentation.ui.fragment.AboutFragment
import com.alexeeff.golangpuzzler.profile.presentation.ui.fragment.ProfileFragment
import com.alexeeff.golangpuzzler.profile.presentation.ui.fragment.UserResultsFragment
import dagger.Component
/**
 * @author Yaroslav Alexeev
 */

@FeatureScope
@Component(modules = [ProfileModule::class], dependencies = [ProfileDependencies::class])
interface ProfileComponent : FeatureApplicationComponent {

    fun inject(fragment: ProfileFragment)

    fun inject(fragment: AboutFragment)

    fun inject(fragment: UserResultsFragment)

    companion object {
        fun create(profileDependencies: ProfileDependencies): ProfileComponent =
                DaggerProfileComponent.builder()
                        .profileDependencies(profileDependencies)
                        .build()
    }
}

@FeatureScope
@Component(dependencies = [ConfigApi::class, CoreApi::class])
interface ProfileDependenciesComponent: ProfileDependencies

/**
 * Используется для инъекции зависимостей внутри активити
 */
internal fun Activity.getComponent() =
        (application as CoreApp).profileFeatureComponent() as ProfileComponent