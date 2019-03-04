package com.anibalbastias.android.marvelapp.base.api.data.dataStoreFactory.series.repository

import com.anibalbastias.android.marvelapp.MarvelApplication
import com.anibalbastias.android.marvelapp.R
import com.anibalbastias.android.marvelapp.base.api.data.dataStoreFactory.common.page.PageData
import com.anibalbastias.android.marvelapp.base.api.data.marvel.MarvelApiService
import com.anibalbastias.android.marvelapp.base.api.domain.series.repository.ISeriesRepository
import com.anibalbastias.android.marvelapp.util.getMarvelAPIHash
import com.anibalbastias.android.marvelapp.util.getTs
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by anibalbastias on 19-03-18.
 */
@Singleton
class SeriesRepositoryImpl @Inject constructor(private val marvelApiService: MarvelApiService) : ISeriesRepository {

    override fun getSeriesData(apiVersion: String, env: String): Flowable<PageData> =
        marvelApiService.getSeriesData(apiVersion, env, buildQueryMap())

    private fun buildQueryMap(): Map<String, String> {

        val map = mutableMapOf<String, String>()
        map["apikey"] = MarvelApplication.appContext.getString(R.string.marvel_public_api_key)
        map["ts"] = getTs()
        map["hash"] = getMarvelAPIHash()

//        map["apikey"] = "54e5b567475f0f2eccd09422e42fb944"
//        map["ts"] = "1551654009"
//        map["hash"] = "932a86166a0d00cff84b12cb02616aa3"

        return map
    }
}