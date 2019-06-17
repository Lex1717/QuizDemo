package com.alexeeff.golangpuzzler.config.di

import com.alexeeff.golangpuzzler.config.presentation.ConfigurationFacade

interface ConfigApi{
    fun configFacade(): ConfigurationFacade
}