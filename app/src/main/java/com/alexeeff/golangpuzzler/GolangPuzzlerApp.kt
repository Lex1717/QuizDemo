package com.alexeeff.golangpuzzler

import com.alexeeff.golangpuzzler.config.di.ConfigComponent
import com.alexeeff.golangpuzzler.core.CoreApp
import com.alexeeff.golangpuzzler.core.di.CoreApi
import com.alexeeff.golangpuzzler.core.di.FeatureApplicationComponent
import com.alexeeff.golangpuzzler.dashboard.di.DashboardComponent
import com.alexeeff.golangpuzzler.di.DaggerMainDependenciesComponent
import com.alexeeff.golangpuzzler.di.MainComponent
import com.alexeeff.golangpuzzler.di.MainDependencies
import com.alexeeff.golangpuzzler.profile.di.DaggerProfileDependenciesComponent
import com.alexeeff.golangpuzzler.profile.di.ProfileComponent
import com.alexeeff.golangpuzzler.profile.di.ProfileDependencies
import com.alexeeff.golangpuzzler.puzzler.di.DaggerPuzzlerDependenciesComponent
import com.alexeeff.golangpuzzler.puzzler.di.PuzzlerComponent
import com.alexeeff.golangpuzzler.puzzler.di.PuzzlerDependencies
import com.arellomobile.mvp.RegisterMoxyReflectorPackages
import io.realm.Realm

/**
 * @author Yaroslav Alexeev
 */
@RegisterMoxyReflectorPackages("com.alexeeff.golangpuzzler.puzzler", "com.alexeeff.golangpuzzler.profile")
class GolangPuzzlerApp : CoreApp(){

    private val puzzlerComponent: PuzzlerComponent by lazy {
        val puzzlerDependencies: PuzzlerDependencies = DaggerPuzzlerDependenciesComponent.builder()
                .coreApi(coreApplicationComponent as CoreApi)
                .configApi(configComponent)
                .build()
        PuzzlerComponent.create(puzzlerDependencies)
    }

    private val coursesComponent: DashboardComponent by lazy {
        DashboardComponent.create(configComponent)
    }

    private val profileComponent: ProfileComponent by lazy {
        val profileDependencies: ProfileDependencies = DaggerProfileDependenciesComponent.builder()
                .coreApi(coreApplicationComponent as CoreApi)
                .configApi(configComponent)
                .build()

        ProfileComponent.create(profileDependencies)
    }

    private val mainComponent: MainComponent by lazy {
        val mainDependencies: MainDependencies = DaggerMainDependenciesComponent.builder()
                .coreApi(coreApplicationComponent as CoreApi)
                .build()
        MainComponent.create(mainDependencies)
    }

    private val configComponent: ConfigComponent by lazy {
        ConfigComponent.create()
    }

    override fun mainApplicationComponent(): FeatureApplicationComponent = mainComponent

    override fun configFeatureComponent(): FeatureApplicationComponent = configComponent

    override fun puzzlerFeatureComponent(): FeatureApplicationComponent = puzzlerComponent

    override fun coursesFeatureComponent(): FeatureApplicationComponent = coursesComponent

    override fun profileFeatureComponent(): FeatureApplicationComponent = profileComponent


    override fun onCreate() {
        super.onCreate()
        Realm.init(applicationContext)
    }
}