package com.anibalbastias.android.marvelapp.base.api.data.dataStoreFactory.series.model

import com.anibalbastias.android.marvelapp.base.api.data.dataStoreFactory.common.section.SectionData
import com.anibalbastias.android.marvelapp.base.api.data.dataStoreFactory.series.model.resources.SeriesResourceData
import com.anibalbastias.android.marvelapp.base.api.data.dataStoreFactory.series.model.thumbnail.ThumbnailIData
import com.google.gson.annotations.SerializedName

class SeriesItemData : SectionData() {

    @field:SerializedName("title")
    var title: String? = null

    @field:SerializedName("startYear")
    var startYear: Int? = null

    @field:SerializedName("endYear")
    var endYear: Int? = null

    @field:SerializedName("rating")
    var rating: String? = null

    @field:SerializedName("type")
    var limited: String? = null

    @field:SerializedName("thumbnail")
    var thumbnail: ThumbnailIData? = null

    @field:SerializedName("creators")
    var creators: SeriesResourceData? = null

    @field:SerializedName("characters")
    var characters: SeriesResourceData? = null

    @field:SerializedName("stories")
    var stories: SeriesResourceData? = null

    @field:SerializedName("comics")
    var comics: SeriesResourceData? = null

    @field:SerializedName("events")
    var events: SeriesResourceData? = null

}