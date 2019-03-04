package com.anibalbastias.android.marvelapp.base.api.data.marvel


import com.anibalbastias.android.marvelapp.base.api.data.dataStoreFactory.common.page.PageData
import io.reactivex.Flowable
import retrofit2.http.*

/**
 * Created by anibalbastias on 3/03/19.
 */

interface MarvelApiService {

    //Get Series data
    @GET("{apiVersion}/{env}/series")
    fun getSeriesData(
        @Path("apiVersion") apiVersion: String,
        @Path("env") env: String,
        @QueryMap parameters: Map<String, String>
    ): Flowable<PageData>
}