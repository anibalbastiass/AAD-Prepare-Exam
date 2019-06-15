package com.anibalbastias.android.marvelapp.module

import androidx.lifecycle.ViewModel
import com.anibalbastias.android.marvelapp.base.module.ViewModelKey
import com.anibalbastias.android.marvelapp.base.module.module.BaseViewModelModule
import com.anibalbastias.android.marvelapp.ui.series.viewmodel.SeriesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule : BaseViewModelModule() {

    @Binds
    @IntoMap
    @ViewModelKey(SeriesViewModel::class)
    internal abstract fun seriesViewModel(viewModel: SeriesViewModel): ViewModel

}