package com.alexeeff.golangpuzzler.core.di

/**
 * Interfaces for Dagger components. Used for getting the components across the multi module project.
 */
interface ApplicationComponent

interface CoreApplicationComponent: ApplicationComponent

interface FeatureApplicationComponent: ApplicationComponent