package com.anibalbastias.android.marvelapp.base.module.module

import com.anibalbastias.android.marvelapp.MarvelApplication
import com.anibalbastias.android.marvelapp.base.api.domain.executor.APIPostExecutionThread
import com.anibalbastias.android.marvelapp.base.api.domain.executor.APIThreadExecutor
import com.anibalbastias.android.marvelapp.base.module.executor.JobExecutor
import com.anibalbastias.android.marvelapp.base.module.executor.UIThread
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class BaseApplicationModule(private val application: MarvelApplication) {

    @Provides
    @Singleton
    fun provideApp(): MarvelApplication = application

    @Provides
    @Singleton
    fun provideAPIThreadExecutor(jobExecutor: JobExecutor): APIThreadExecutor = jobExecutor

    @Provides
    @Singleton
    fun provideAPIPostExecutionThread(uiThread: UIThread): APIPostExecutionThread = uiThread
}