package com.anibalbastias.android.marvelapp.base.api.data.dataStoreFactory.common.section

import com.anibalbastias.android.marvelapp.base.api.data.dataStoreFactory.common.TypeData
import com.anibalbastias.android.marvelapp.base.api.data.dataStoreFactory.series.model.resources.SeriesResourceItemData
import com.google.gson.annotations.SerializedName

open class SectionData : TypeData() {

    @field:SerializedName("id")
    var id: Int? = null

    @field:SerializedName("description")
    var description: String? = null

    @field:SerializedName("resourceURI")
    var resourceURI: String? = null

    @field:SerializedName("modified")
    var modified: String? = null

    @field:SerializedName("urls")
    var urls: List<UrlItemData?>? = null

    @field:SerializedName("next")
    var next: SeriesResourceItemData? = null

    @field:SerializedName("previous")
    var previous: SeriesResourceItemData? = null

    @field:SerializedName("etag")
    var etag: String? = null
}