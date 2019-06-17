package com.alexeeff.golangpuzzler.config.di

import com.alexeeff.golangpuzzler.config.data.ConfigRepositoryImpl
import com.alexeeff.golangpuzzler.config.data.storage.ConfigRealmWrapper
import com.alexeeff.golangpuzzler.config.data.storage.ConfigurationStorage
import com.alexeeff.golangpuzzler.config.data.storage.ConfigurationStorageImpl
import com.alexeeff.golangpuzzler.config.domain.ConfigRepository
import com.alexeeff.golangpuzzler.config.domain.interactor.GetUserInfoInteractor
import com.alexeeff.golangpuzzler.config.domain.interactor.SetUserInfoInteractor
import com.alexeeff.golangpuzzler.config.presentation.ConfigurationFacade
import com.alexeeff.golangpuzzler.config.presentation.ConfigurationFacadeImpl
import com.alexeeff.golangpuzzler.core.di.FeatureScope
import com.alexeeff.golangpuzzler.core.interfaces.RealmWrapper
import dagger.Module
import dagger.Provides

/**
 * @author Yaroslav Alexeev
 */
@Module
class ConfigModule {
    @Provides
    @FeatureScope
    fun provideConfigStorage(
            realmWrapper: RealmWrapper
    ): ConfigurationStorage =
            ConfigurationStorageImpl(realmWrapper)

    @Provides
    @FeatureScope
    fun provideConfigRepository(
            configurationStorage: ConfigurationStorage
    ): ConfigRepository =
            ConfigRepositoryImpl(configurationStorage)

    @Provides
    fun provideSetUserInfoInteractor(
            configRepository: ConfigRepository
    ): SetUserInfoInteractor =
            SetUserInfoInteractor(configRepository)

    @Provides
    fun provideGetUserInfoInteractor(
            configRepository: ConfigRepository
    ): GetUserInfoInteractor =
            GetUserInfoInteractor(configRepository)

    @Provides
    @FeatureScope
    fun provideConfigFacade(
            setInfoInteractor: SetUserInfoInteractor,
            getInfoInteractor: GetUserInfoInteractor
    ): ConfigurationFacade =
            ConfigurationFacadeImpl(setInfoInteractor, getInfoInteractor)

    @Provides
    @FeatureScope
    fun provideRealmWrapper(): RealmWrapper = ConfigRealmWrapper()
}