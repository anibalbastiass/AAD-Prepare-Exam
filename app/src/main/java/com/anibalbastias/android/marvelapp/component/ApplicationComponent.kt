package com.anibalbastias.android.marvelapp.component

import com.anibalbastias.android.marvelapp.module.ApplicationModule
import com.anibalbastias.android.marvelapp.module.MarvelAPIModule
import com.anibalbastias.android.marvelapp.module.ViewModelModule
import com.anibalbastias.android.marvelapp.MainActivity
import com.anibalbastias.android.marvelapp.base.module.component.BaseApplicationComponent
import com.anibalbastias.android.marvelapp.module.MarvelRepositoryModule
import com.anibalbastias.android.marvelapp.ui.series.SeriesFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        MarvelRepositoryModule::class,
        ViewModelModule::class,
        MarvelAPIModule::class]
)

interface ApplicationComponent : BaseApplicationComponent, FragmentInjector {
    fun inject(mainActivity: MainActivity)
}

interface FragmentInjector {
    fun inject(seriesFragment: SeriesFragment)
}