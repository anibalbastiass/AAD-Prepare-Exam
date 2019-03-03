package com.anibalbastias.android.marvelapp.base.module.component

import com.anibalbastias.android.marvelapp.MarvelApplication
import com.anibalbastias.android.marvelapp.base.module.executor.JobExecutor
import com.anibalbastias.android.marvelapp.base.module.executor.UIThread


interface BaseApplicationComponent {

    fun inject(application: MarvelApplication)
    fun threadExecutor(): JobExecutor
    fun postExecutionThread(): UIThread
}