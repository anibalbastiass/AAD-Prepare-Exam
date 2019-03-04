package com.anibalbastias.android.marvelapp.base.api.data.dataStoreFactory.series.model.resources

import com.google.gson.annotations.SerializedName

class SeriesResourceData {

    @field:SerializedName("available")
    var available: Int? = null

    @field:SerializedName("collectionURI")
    var collectionURI: String? = null

    @field:SerializedName("items")
    var items: List<SeriesResourceItemData>? = null

    @field:SerializedName("returned")
    var returned: Int? = null
}