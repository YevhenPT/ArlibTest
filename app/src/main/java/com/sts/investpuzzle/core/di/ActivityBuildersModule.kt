package com.sts.investpuzzle.core.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ActivityBuildersModule {

    companion object{
        @Provides
        @Singleton
        @JvmStatic
        fun cicerone(): Cicerone<Router> {
            return Cicerone.create()
        }

    }
}