package com.alexeeff.golangpuzzler.puzzler.di

import android.app.Activity
import com.alexeeff.golangpuzzler.config.di.ConfigApi
import com.alexeeff.golangpuzzler.core.CoreApp
import com.alexeeff.golangpuzzler.core.di.CoreApi
import com.alexeeff.golangpuzzler.core.di.FeatureApplicationComponent
import com.alexeeff.golangpuzzler.core.di.FeatureScope
import com.alexeeff.golangpuzzler.puzzler.presentation.ui.fragment.CourseListFragment
import com.alexeeff.golangpuzzler.puzzler.presentation.ui.fragment.PuzzlerFragment
import com.alexeeff.golangpuzzler.puzzler.presentation.ui.fragment.ResultFragment
import dagger.Component

/**
 * @author Yaroslav Alexeev
 */
@FeatureScope
@Component(modules = [PuzzlerModule::class], dependencies = [PuzzlerDependencies::class])
interface PuzzlerComponent : FeatureApplicationComponent {

    fun inject(fragment: PuzzlerFragment)
    fun inject(fragment: ResultFragment)
    fun inject(fragment: CourseListFragment)

    companion object {
        fun create(puzzlerDependencies: PuzzlerDependencies): PuzzlerComponent =
                DaggerPuzzlerComponent.builder()
                        .puzzlerDependencies(puzzlerDependencies)
                        .build()
    }
}
@FeatureScope
@Component(dependencies = [ConfigApi::class, CoreApi::class])
interface PuzzlerDependenciesComponent: PuzzlerDependencies
/**
 * Used for di in fragments
 */
internal fun Activity.getComponent() = (application as CoreApp).puzzlerFeatureComponent() as PuzzlerComponent