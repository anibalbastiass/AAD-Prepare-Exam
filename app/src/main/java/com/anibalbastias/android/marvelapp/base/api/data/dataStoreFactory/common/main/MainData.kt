package com.anibalbastias.android.marvelapp.base.api.data.dataStoreFactory.common.main

import com.anibalbastias.android.marvelapp.base.api.data.dataStoreFactory.series.model.SeriesItemData
import com.google.gson.annotations.SerializedName

class MainData {

    @field:SerializedName("offset")
    var offset: Int? = null

    @field:SerializedName("limit")
    var limit: Int? = null

    @field:SerializedName("total")
    var total: Int? = null

    @field:SerializedName("count")
    var count: Int? = null

    @field:SerializedName("results")
    var results: List<SeriesItemData>? = null
//    var results: List<SectionData>? = null
}