package com.anibalbastias.android.marvelapp.base.api.domain.series.repository

import com.anibalbastias.android.marvelapp.base.api.data.dataStoreFactory.common.page.PageData
import io.reactivex.Flowable

/**
 * Created by anibalbastias on 3/19/18.
 */
interface ISeriesRepository {

    fun getSeriesData(
        apiVersion: String,
        env: String
    ): Flowable<PageData>
}