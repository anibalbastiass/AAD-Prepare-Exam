package com.anibalbastias.android.marvelapp.base.api.data.dataStoreFactory.common.section

import com.google.gson.annotations.SerializedName

data class UrlItemData(

    @field:SerializedName("type")
    var type: String? = null,

    @field:SerializedName("url")
    var url: String? = null
)