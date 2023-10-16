package com.sts.investpuzzle.core.di

import android.content.Context
import com.sts.investpuzzle.core.data.network.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
abstract class NetworkModule {

      @Binds
    abstract fun provideMedicineRepository(medicineRepositoryImp: MedicineRepositoryImp):MedicineRepository


}