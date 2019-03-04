package com.anibalbastias.android.marvelapp.base.api.data.dataStoreFactory.common.page

import com.anibalbastias.android.marvelapp.base.api.data.dataStoreFactory.common.main.MainData
import com.google.gson.annotations.SerializedName

class PageData {

    @field:SerializedName("code")
    var code: Int? = null

    @field:SerializedName("status")
    var status: String? = null

    @field:SerializedName("copyright")
    var copyright: String? = null

    @field:SerializedName("attributionText")
    var attributionText: String? = null

    @field:SerializedName("attributionHTML")
    var attributionHTML: String? = null

    @field:SerializedName("data")
    var data: MainData? = null

    @field:SerializedName("etag")
    var etag: String? = null
}