package com.anibalbastias.android.marvelapp.base.module.module

import android.arch.lifecycle.ViewModelProvider
import com.anibalbastias.android.marvelapp.base.module.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class BaseViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}