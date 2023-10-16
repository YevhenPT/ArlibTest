package com.sts.investpuzzle.core.di

import android.content.Context
import com.sts.investpuzzle.MyApplication
import com.sts.investpuzzle.core.data.network.ApiEndPoint
import com.sts.investpuzzle.core.data.prefs.AppPreferencesHelper
import com.sts.investpuzzle.core.data.prefs.PreferencesHelper
import com.sts.investpuzzle.core.data.session.AppSessionHelper
import com.sts.investpuzzle.core.data.session.SessionHelper
import com.sts.investpuzzle.utils.rx.AppSchedulerProvider
import com.sts.investpuzzle.utils.rx.SchedulerProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    internal fun providePreferencesHelper(@ApplicationContext context: Context): PreferencesHelper = AppPreferencesHelper(context)

    @Singleton
    @Provides
    internal fun provideApiEndPoint(preferencesHelper: PreferencesHelper): ApiEndPoint  = ApiEndPoint(preferencesHelper)

    @Singleton
    @Provides
    internal fun provideSessionHelper(@ApplicationContext context: Context, preferencesHelper: PreferencesHelper): SessionHelper = AppSessionHelper(context, preferencesHelper)

    @Provides
    internal fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    internal fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()
}