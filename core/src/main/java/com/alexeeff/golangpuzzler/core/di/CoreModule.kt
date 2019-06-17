package com.alexeeff.golangpuzzler.core.di

import android.content.Context
import android.content.SharedPreferences
import com.alexeeff.golangpuzzler.core.SHARED_PREFS
import com.alexeeff.golangpuzzler.core.cicerone.LocalCiceroneHolder
import com.alexeeff.golangpuzzler.core.entity.NetworkManagerImpl
import com.alexeeff.golangpuzzler.core.interfaces.NetworkManager
import dagger.Module
import dagger.Provides

/**
 * @author Yaroslav Alexeev
 */

@Module
class CoreModule(val context: Context) {
    @FeatureScope
    @Provides fun provideNetworkManager(): NetworkManager = NetworkManagerImpl(context)

    @FeatureScope
    @Provides
    fun provideSharedPrefs(): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
    }

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    @FeatureScope
    fun provideLocalNavigationHolder(): LocalCiceroneHolder {
        return LocalCiceroneHolder()
    }

}