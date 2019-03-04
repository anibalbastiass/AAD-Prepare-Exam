package com.anibalbastias.android.marvelapp.base.api.data.dataStoreFactory.series.model.resources

import com.google.gson.annotations.SerializedName

class SeriesResourceItemData {

    @field:SerializedName("resourceURI")
    var resourceURI: String? = null

    @field:SerializedName("name")
    var name: String? = null

    @field:SerializedName("role")
    var role: String? = null
}