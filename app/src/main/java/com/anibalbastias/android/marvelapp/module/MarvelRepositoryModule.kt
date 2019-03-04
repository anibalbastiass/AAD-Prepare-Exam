package com.anibalbastias.android.marvelapp.module

import com.anibalbastias.android.marvelapp.base.api.data.dataStoreFactory.series.repository.SeriesRepositoryImpl
import com.anibalbastias.android.marvelapp.base.api.domain.series.repository.ISeriesRepository
import dagger.Binds
import dagger.Module

@Module
abstract class MarvelRepositoryModule {

    @Binds
    abstract fun bindSeriesDataRepository(repository: SeriesRepositoryImpl): ISeriesRepository

}